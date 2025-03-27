package net.smooth.zhenxiactivity.controller;
import lombok.Getter;
import net.smooth.zhenxiactivity.dto.response.ApiResponse;
import net.smooth.zhenxiactivity.dto.response.AuthorizationResult;
import net.smooth.zhenxiactivity.dto.response.UserInfo;
import net.smooth.zhenxiactivity.service.ApiService;
import net.smooth.zhenxiactivity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
@Getter
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private ApiService appService;
    @Autowired
    private UserService userService;

    @GetMapping("/authorization")
    public ApiResponse<AuthorizationResult> authorization(@RequestParam String authorization_code)throws  Exception {
        ApiResponse<AuthorizationResult> callResult = this.appService.callApi("buyer.oauth2.authorization", "", new HashMap<String, Object>() {{
                put("authorization_code", authorization_code);
            }});
        if(callResult.getData()!=null && callResult.getData().getUserInfo()!=null)
        {
            UserInfo user = callResult.getData().getUserInfo();
            if(userService.getUserById(user.getUserId())==null)
            {
                userService.addUser(user);
            }
        }
        return callResult;
    }

    @GetMapping("/info")
    public ApiResponse<UserInfo> getUserInfo(@RequestParam String access_token)throws  Exception {
        ApiResponse<UserInfo> callResult = this.appService.callApi("buyer.oauth2.info", access_token, new HashMap<String, Object>());
        return callResult;
    }

    @GetMapping("/refreshToken")
    public ApiResponse<UserInfo> refreshToken(@RequestParam String access_token,@RequestParam String refresh_token)throws  Exception {
        ApiResponse<UserInfo> callResult = this.appService.callApi("buyer.oauth2.refresh", access_token, new HashMap<String, Object>() {
            {
                put("refresh_token", refresh_token);
            }
        });
        return callResult;
    }

    @GetMapping("/index")
    public String index(Model model,String authorization_code) {
        ApiResponse<AuthorizationResult> response  = new ApiResponse<>();
        try {
            response = this.appService.callApi("buyer.oauth2.authorization", "", new HashMap<String, Object>() {{
                put("authorization_code", authorization_code);
            }});
        }catch(Exception e)
        {
            model.addAttribute("error",e.getMessage());
        }
        model.addAttribute("appKey",this.appService.getAppKey());
        model.addAttribute("appSecret",this.appService.getAppSecret());
        model.addAttribute("host",this.appService.getHost());
        model.addAttribute("url",this.appService.getServiceUrl());
        model.addAttribute("authorization_code",authorization_code);
        model.addAttribute("request",this.appService.getRequestData());
        model.addAttribute("response",this.appService.getResponseData());
        return "index";
    }
}