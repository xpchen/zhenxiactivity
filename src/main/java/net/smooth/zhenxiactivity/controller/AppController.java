package net.smooth.zhenxiactivity.controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import net.smooth.zhenxiactivity.dto.request.CouponDispatchRequest;
import net.smooth.zhenxiactivity.dto.request.GenerateBadgeRequest;
import net.smooth.zhenxiactivity.dto.request.GenerateJournalRequest;
import net.smooth.zhenxiactivity.dto.request.UpdatePrizeDeliveryAddressRequest;
import net.smooth.zhenxiactivity.dto.response.*;
import net.smooth.zhenxiactivity.model.Prize;
import net.smooth.zhenxiactivity.model.ScratchRecord;
import net.smooth.zhenxiactivity.model.UserBadge;
import net.smooth.zhenxiactivity.model.UserJournal;
import net.smooth.zhenxiactivity.service.*;
import net.smooth.zhenxiactivity.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Getter
@Controller
@RequestMapping("/app")
public class AppController {
    // 存儲圖片的目錄路徑
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    private ApiService appService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserBadgeService userBadgeService;

    @Autowired
    private UserJournalService userJournalService;

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private ScratchRecordService scratchRecordService;

    private String getCurrentUserId(){
        return "9342111";
    }

    private UserInfo getCurrentUser() {
        return this.userService.getUserById("9342111");
    }

    @GetMapping("/authorization")
    public ResponseEntity<?> authorization(HttpServletRequest request,@RequestParam String authorization_code)throws  Exception {
        boolean isMock = false;
        if (request.getParameter("isMock")!=null) {
            isMock = "true".equalsIgnoreCase(request.getParameter("isMock"));
        }
        if (!isMock) {
            ApiResponse<AuthorizationResult> callResult = this.appService.callApi("buyer.oauth2.authorization", TokenUtil.getAccessToken(), new HashMap<String, Object>() {{
                put("authorization_code", authorization_code);
            }});
            if (callResult.getData() != null && callResult.getData().getUserInfo() != null) {
                UserInfo user = callResult.getData().getUserInfo();
                if (userService.getUserById(user.getUserId()) == null) {
                    userService.addUser(user);
                }
            }
            return ResponseEntity.ok(callResult);
        } else {
            AuthorizationResult result = new AuthorizationResult();
            UserInfo userInfo = getCurrentUser();
            result.setAccessToken("6f1746e96fab03a978810ea7017faacb");
            result.setRefresh_token("3bfded03ddffc77b82afb3bf1a851673");
            result.setUserInfo(userInfo);
            return ResponseEntity.ok(new ApiResponse<>(result));
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request)throws  Exception {
        boolean isMock = false;
        if (request.getParameter("isMock") != null) {
            isMock = "true".equalsIgnoreCase(request.getParameter("isMock"));
        }
        if (isMock) {
            return ResponseEntity.ok(new ApiResponse<UserInfo>(getCurrentUser()));
        } else {
            ApiResponse<UserInfo> callResult = this.appService.callApi("buyer.oauth2.info", TokenUtil.getAccessToken(), new HashMap<String, Object>());
            return ResponseEntity.ok(callResult);
        }
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request,@RequestParam String refresh_token)throws  Exception {
        boolean isMock = false;
        if (request.getParameter("isMock") != null) {
            isMock = "true".equalsIgnoreCase(request.getParameter("isMock"));
        }
        if (isMock) {
            AuthorizationResult result = new AuthorizationResult();
            UserInfo userInfo = getCurrentUser();
            result.setAccessToken("6f1746e96fab03a978810ea7017faacb");
            result.setRefresh_token("3bfded03ddffc77b82afb3bf1a851673");
            result.setUserInfo(userInfo);
            return ResponseEntity.ok(result);
        } else {
            ApiResponse<AuthorizationResult> callResult = this.appService.callApi("buyer.oauth2.refresh", TokenUtil.getAccessToken(), new HashMap<String, Object>() {
                {
                    put("refresh_token", refresh_token);
                }
            });
            return ResponseEntity.ok(callResult);
        }
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

    @GetMapping("/badges")
    public ResponseEntity<?> getUserBadges(@RequestParam("badgeType")String badgeType)
    {
        List<UserBadge> userBadges = userBadgeService.getUserBadgesByUserId(getCurrentUserId(),badgeType);
        return ResponseEntity.ok(new ApiResponse<List<UserBadge>>(userBadges));
    }
    @GetMapping("/badges/{id}")
    public ResponseEntity<?> getUserBadgeById(@PathVariable("id") Integer id) {
        // 获取指定 id 的 journal 的逻辑
        UserBadge userBadge = this.userBadgeService.getUserBadgeById(id.intValue());
        return ResponseEntity.ok(new ApiResponse(userBadge));
    }


    @PostMapping("/generateBadge")
    public ResponseEntity<?> generateBadge(@RequestBody GenerateBadgeRequest request) throws Exception {
        if (request.getNickName() == null || request.getNickName().isEmpty()) {
            return ResponseEntity.badRequest().body("NickName cannot be empty");
        }
        try {
            UserBadge userBadge = new UserBadge();
            userBadge.setUserId(getCurrentUserId());
            userBadge.setNickName(request.getNickName());
            userBadge.setType(request.getBadgeType());
            userBadge.setDateEarned(new Date());
            userBadgeService.addUserBadge(userBadge);
            return ResponseEntity.ok(new ApiResponse(userBadge));
        } catch (Exception ex) {
            System.out.print(ex.getStackTrace());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/badgeImage")
    public ResponseEntity<?> getBadgeImage(@RequestParam("badgeId") Integer badgeId) throws IOException {
        if (badgeId == null) {
            return ResponseEntity.badRequest().body("badgeId is required");
        }
        UserBadge badge = this.userBadgeService.getUserBadgeById(badgeId);
        if(badge==null)
        {
            return ResponseEntity.notFound().build();  // 若找不到圖片，返回 404
        }
        // 創建文件對象
        File imageFile = new File(uploadDir,badge.getPath());
        if (!imageFile.exists()) {
            return ResponseEntity.notFound().build();  // 若找不到圖片，返回 404
        }
        // 讀取圖片文件
        try (InputStream in = new FileInputStream(imageFile)) {
            byte[] imageBytes = in.readAllBytes();
            // 返回圖片數據
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")  // 設置圖片格式
                    .body(imageBytes);
        }
    }

    @PostMapping("/uploadJournal")
    public ResponseEntity<?> saveJournal(@RequestParam("file") MultipartFile file, @RequestParam("journalId") Integer journalId){
        // 檢查文件是否為空
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No badge file uploaded.");
        }
        if (journalId == null) {
            return ResponseEntity.badRequest().body("badgeId is required");
        }
        if (journalId==null) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }
        try {
            UserJournal userJournal = userJournalService.getUserJournalById(journalId);
            // 檢查目錄是否存在，若不存在則創建
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            // 獲取文件的名稱
            String fileName = userJournal.getUserId()+"_"+userJournal.getJournalId() + "_" + System.currentTimeMillis() + ".jpg"; // 可以根據需求調整文件名
            // 保存文件
            File serverFile = new File(uploadDir, fileName);
            file.transferTo(serverFile); // 保存文件到服務器
            userJournal.setPath(fileName);
            userJournalService.updateUserJournal(userJournal);
            return ResponseEntity.ok(new ApiResponse(userJournal));
            // 返回成功的響應
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload image due to an error.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/journals")
    public ResponseEntity<?> getUserJournals(@RequestParam("journalType")String journalType)
    {
        List<UserJournal> userJournals = userJournalService.getUserJournalsByUserId(getCurrentUserId(),journalType);
        return ResponseEntity.ok(new ApiResponse<>(userJournals));
    }

    @GetMapping("/journals/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable("id") Integer id) {
        // 获取指定 id 的 journal 的逻辑
        UserJournal userJournal = this.userJournalService.getUserJournalById(id.intValue());
        return ResponseEntity.ok(new ApiResponse<>(userJournal));
    }

    @PostMapping("/generateJournal")
    public ResponseEntity<?> generateJournal(@RequestBody GenerateJournalRequest request) throws Exception {
        if (request.getNickName() == null || request.getNickName().isEmpty()) {
            return ResponseEntity.badRequest().body("NickName cannot be empty");
        }
        try {
            UserJournal userJournal = new UserJournal();
            userJournal.setUserId(getCurrentUserId());
            userJournal.setNickName(request.getNickName());
            userJournal.setType(request.getJournalType());
            userJournal.setPublishedDate(new Date());
            userJournal.setContent(request.getContent());
            userJournalService.addUserJournal(userJournal);
            return ResponseEntity.ok(new ApiResponse<>(userJournal));
        } catch (Exception ex) {
            System.out.print(ex.getStackTrace());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/journalImage")
    public ResponseEntity<?> getJournalImage(@RequestParam("journalId") Integer journalId) throws IOException {
        if (journalId == null) {
            return ResponseEntity.badRequest().body("journalId is required");
        }
        UserJournal journal = this.userJournalService.getUserJournalById(journalId);
        if(journal==null)
        {
            return ResponseEntity.notFound().build();  // 若找不到圖片，返回 404
        }
        // 創建文件對象
        File imageFile = new File(uploadDir,journal.getPath());
        if (!imageFile.exists()) {
            return ResponseEntity.notFound().build();  // 若找不到圖片，返回 404
        }
        // 讀取圖片文件
        try (InputStream in = new FileInputStream(imageFile)) {
            byte[] imageBytes = in.readAllBytes();
            // 返回圖片數據
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")  // 設置圖片格式
                    .body(imageBytes);
        }
    }

    @PostMapping("/uploadBadge")
    public ResponseEntity<?> saveBadge(@RequestParam("file") MultipartFile file, @RequestParam("badgeId") Integer badgeId){
        // 檢查文件是否為空
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No badge file uploaded.");
        }
        if (badgeId == null) {
            return ResponseEntity.badRequest().body("badgeId is required");
        }
        if (badgeId==null) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }
        try {
            UserBadge userBadge = userBadgeService.getUserBadgeById(badgeId);
            // 檢查目錄是否存在，若不存在則創建
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            // 獲取文件的名稱
            String fileName = userBadge.getUserId()+"_"+userBadge.getBadgeId() + "_" + System.currentTimeMillis() + ".jpg"; // 可以根據需求調整文件名
            // 保存文件
            File serverFile = new File(uploadDir, fileName);
            file.transferTo(serverFile); // 保存文件到服務器
            userBadge.setPath(fileName);
            userBadgeService.updateUserBadge(userBadge);
            // 返回成功的響應
            return ResponseEntity.ok(new ApiResponse<>(userBadge));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload image due to an error.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/drawPrize")
    public ResponseEntity<?> drawPrize() {
        try {
            String userId = getCurrentUserId();
            Prize prize = this.prizeService.getRandomPrize();
            ScratchRecord scratchRecord = new ScratchRecord();
            scratchRecord.setPrizeId(prize.getId());
            scratchRecord.setUserId(userId);
            scratchRecord.setWinner(true);
            scratchRecordService.addScratchRecord(scratchRecord);
            UserScratchResult result = new UserScratchResult();
            result.setPrizeName(prize.getPrizeName());
            result.setEffectiveDays(prize.getEffectiveDays());
            result.setPrizeType(prize.getPrizeType());
            result.setWinningTime(scratchRecord.getCreatedAt());
            result.setRecordId(scratchRecord.getId());
            return ResponseEntity.ok(new ApiResponse<>(result));
        }catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(new ApiResponse<>(new ErrorResponse(){{
                    setCode(500);
                    setMsg(ex.getMessage());
            }}));
        }
    }

    @PostMapping("/updatePrizeDeliveryAddress")
    public ResponseEntity<?>  UpdatePrizeDeliveryAddress(@RequestBody UpdatePrizeDeliveryAddressRequest request){
        try{
            if(request.getRecordId()==null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(new ErrorResponse() {{
                    setCode(500);
                    setMsg("recordId is required");
                }}));
            }
            CouponDispatchRequest request1 = new CouponDispatchRequest();
            ScratchRecord record = this.scratchRecordService.getScratchRecordById(request.getRecordId());
            record.setAddressLine1(request.getAddressLine1());
            record.setAddressLine2(request.getAddressLine2());
            record.setCity(request.getCity());
            record.setCountry(request.getCountry());
            record.setState(request.getState());
            record.setRecipientName(request.getRecipientName());
            record.setPhoneNumber(request.getPhoneNumber());
            if(record==null)
            {
                return ResponseEntity.notFound().build();
            }
            this.scratchRecordService.updateScratchRecord(record);
            return ResponseEntity.badRequest().body(new ApiResponse<>("Update address succeeded"));
        }catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(new ApiResponse<>(new ErrorResponse(){{
                setCode(500);
                setMsg(ex.getMessage());
            }}));
        }
    }
}