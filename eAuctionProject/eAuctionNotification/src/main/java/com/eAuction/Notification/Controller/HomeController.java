package com.eAuction.Notification.Controller;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eAuction.Notification.domain.watchlist;
import com.eAuction.Notification.domain.UserEmail;
import com.eAuction.Notification.domain.Response;
import com.eAuction.Notification.service.WatchlistService;
import com.eAuction.Notification.service.WatchlistServiceImpl;
import com.eAuction.Notification.service.EmailService;
import com.eAuction.Notification.service.EmailServiceImpl;
import com.eAuction.Notification.service.UserEmailServiceImpl;

import java.util.List;


@RestController
public class HomeController {


    @Autowired
    public EmailServiceImpl emailService;
    @Autowired
    WatchlistServiceImpl watchlistService;
    @Autowired
    UserEmailServiceImpl userEmailService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }


    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String createMail(Model model) {
        emailService.sendSimpleMessage("mantraliu1215@gmail.com",
                "test", "test");

        return "Sent";
    }


    @RequestMapping(value = "/notifyNewBid", method=RequestMethod.POST)
    public Response notifyNewBid(
            @RequestParam("postingID") String postingID
            , @RequestParam("itemName") String itemName
            , @RequestParam("sellerID") String id
            , HttpServletResponse response
    ) {
        String subject = "New Bid on Item " + itemName;
        String text = "Hi, there is a new bid on your posting " + itemName + ". For details go postingDetailsAuction/"+postingID;
        String email = userEmailService.findByUserId(Integer.parseInt(id));
        emailService.sendSimpleMessage(email, subject, text);

        return new Response(true, "");
    }

    @RequestMapping(value = "/notifyLostBid", method=RequestMethod.POST)
    public Response notifyLostBid(
            @RequestParam("postingID") String postingID
            , @RequestParam("itemName") String itemName
            , @RequestParam("bidderId") String id
            , HttpServletResponse response
    ) {
        String subject = "Lost Bid on Item " + itemName;
        String text = "Hi, there is a higher bid on item " + itemName + ". For details go postingDetailsAuction/"+postingID;
        String email = userEmailService.findByUserId(Integer.parseInt(id));
        emailService.sendSimpleMessage(email, subject, text);

        return new Response(true, "");
    }

    @RequestMapping(value = "/notifyExpire", method=RequestMethod.POST)
    public Response notifyExpire(
            @RequestParam("ids") String ids
            , HttpServletResponse response
    ) {
        String[] pairs = ids.split(",");
        for(String pair:pairs){
            String postingID = pair.substring(1,pair.length()-1).split(" ")[0];
            String id = pair.substring(1,pair.length()-1).split(" ")[1];
            String subject = "Posting Will Expire in 1 Hour";
            String text = "Hi, the posting you bid on will expire in one hour. For details go postingDetailsAuction/"+postingID;
            String email = userEmailService.findByUserId(Integer.parseInt(id));
            emailService.sendSimpleMessage(email, subject, text);
        }
        return new Response(true, "");
    }

    @RequestMapping(value = "/notifyWatchlist", method=RequestMethod.POST)
    public Response notifyWatchlist(
            @RequestParam("price") String price
            , @RequestParam("itemId") String itemId
            , @RequestParam("postingID") String postingID
            , HttpServletResponse response
    ) {

        List<watchlist> watchlists = watchlistService.findByPriceAndItemId(Integer.parseInt(itemId),Double.parseDouble(price));
        for(watchlist item : watchlists){
            String subject = "Watchlist Update -- New Posting on Item " + item.getItemName();
            String text = "Hi, there is a new posting on item " + item.getItemName() + ". The price is $"+ price +". For details go postingDetailsAuction/"+postingID;
            emailService.sendSimpleMessage(item.getEmail(), subject, text);

        }
        return new Response(true, "");
    }


    @RequestMapping(value="/addWatchlist", method=RequestMethod.POST)
    public watchlist addWatchlist(@RequestParam("userid") String userid, @RequestParam("itemId") String itemId,
                              @RequestParam("itemName") String itemName, @RequestParam("price") String price) {
        String email = userEmailService.findByUserId(Integer.parseInt(userid));
        watchlist list = new watchlist(email, Integer.parseInt(itemId), itemName, Double.parseDouble(price), userid);
        watchlistService.addToList(list);
        return list;
    }

    @RequestMapping(value="/addEmail", method=RequestMethod.POST)
    public Response addEmail(@RequestParam("email") String email, @RequestParam("userid") String id) {
        userEmailService.addEmail(email, Integer.parseInt(id));
        return new Response(true, "");
    }


    @RequestMapping(value="/watchlist",method=RequestMethod.POST)
    public List<watchlist> listAddresses(@RequestParam("userid") String userid){
        return watchlistService.findByUserName(userid);
    }

    @RequestMapping(value = "/deleteWatchlist", method=RequestMethod.POST)
    public Response deleteWatchlist(@RequestParam String id) {
        watchlistService.delete(Integer.parseInt(id));
        return new Response(true, "");
    }
    


}
