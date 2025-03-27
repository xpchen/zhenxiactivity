//package net.smooth.zhenxiactivity.test;
//
//import com.example.demo.model.GenerateBadgeRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.smooth.zhenxiactivity.dto.request.GenerateBadgeRequest;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//public class AppControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testGenerateBadge() throws Exception {
//
//        // 創建一個 GenerateBadgeRequest 對象
//        GenerateBadgeRequest request = new GenerateBadgeRequest();
//        request.setNickName("JohnDoe");
//        // 使用 MockMvc 發送 POST 請求並檢查響應
//        mockMvc.perform(post("/app/generateBadge")
//                        .contentType("application/json")  // 設置 Content-Type 為 JSON
//                        .content(new ObjectMapper().writeValueAsString(request)))  // 設置請求體
//                .andExpect(status().isOk())  // 檢查狀態碼
//                .andExpect(content().string("Badge generated for JohnDoe"));  // 檢查返回內容
//    }
//}
