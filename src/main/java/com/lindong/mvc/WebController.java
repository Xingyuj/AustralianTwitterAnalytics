package com.lindong.mvc;

/**
 * Created by lld on 9/05/2016.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IPAddress;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import twitterservice.TwitterStreamService;
import cloud.dao.DBManager;
import cloud.dao.PlainStatusCBD;


@Controller
public class WebController {
    DBManager dbManager = new DBManager("127.0.0.1:5984", "admin", "admin", "ccdb");

    static {
        try {
            TwitterStreamService tss = new TwitterStreamService();
            tss.runTwitterService();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String printWelcome() {
        System.out.println("666666666666666666");
        JSONObject ret = new JSONObject();
        ret.put("status", "123");
        return "huahua";
    }


    @RequestMapping(value = "/testHtml",method = RequestMethod.GET)
    public String test(Model model) throws IOException{
    	BufferedReader br = new BufferedReader(new FileReader("/IpAddress.txt"));
		String ip = br.readLine();
		String port = br.readLine();
		IPAddress ipAddress = new IPAddress(ip, port);
    	model.addAttribute("ipAddress", ipAddress);
        return "test";
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("/IpAddress.txt"));
		String ip = br.readLine();
		String port = br.readLine();
		IPAddress ipAddress = new IPAddress(ip, port);
    	model.addAttribute("ipAddress", ipAddress);
        return "index";
    }

    @RequestMapping(value = "/getNewTweet", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getNewTweet(HttpServletRequest request, HttpServletResponse response) {
//        List<Map<String, Object>> tweets = new ArrayList<Map<String, Object>>();
//        Tweet tweet1 = new Tweet("hello", "Preston");
//        Tweet tweet2 = new Tweet("no hello", "Brunswick");
//
//        tweets.add(tweet1.map);
//        tweets.add(tweet2.map);
//
//        JSONArray str = JSONArray.fromObject(tweets);
        String currentTime = request.getParameter("currentTime");
        JSONObject ret = new JSONObject();
        List<PlainStatusCBD> searched = dbManager.findRecentTwitter(Long.parseLong(currentTime));
        ret.put("list", searched);
        return ret.toString();
//        List<Tweet> tweets = new ArrayList<Tweet>();


//        synchronized (searched) {

//            for (int i = 0; i < searched.size(); i++) {
//                tweets.add(new Tweet(searched.get(i).getPlace().getName(), searched.get(i).getText()));
//            }
//            searched.clear();
//        }



//        ret.put("list", searched);
//        List<Test> s = new ArrayList<Test>();
//        s.add(new Test("567", "999"));
//        s.add(new Test("9999", null));
//        s.add(new Test("533", "333"));


        //searched.clear();

//        System.out.println("WWWWWWWWWWWWWWWWW");
        //synchronized (searched) {

//            ret.put("list", searched);
//            searched.clear();

        //}

        //return ret.toString();
//
//        try {
//            PrintWriter out = response.getWriter();
//            out.print(str.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(str.toString());
    }
}
