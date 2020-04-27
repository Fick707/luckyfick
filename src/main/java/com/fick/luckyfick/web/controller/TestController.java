package com.fick.luckyfick.web.controller;

import com.fick.common.utils.file.CommonFileWriter;
import com.fick.luckyfick.constants.ResultCode;
import com.fick.luckyfick.model.WebResult;
import com.fick.luckyfick.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @name: HistoryAnalysisController
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/1
 **/
@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/append", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<String> append(HttpServletResponse response, @RequestParam String fileName,@RequestParam String data) throws IOException {
        CommonFileWriter fileWriter = new CommonFileWriter(fileName);
        fileWriter.append(data);
        return WebResult.success(ResultCode.SERVER_SUCCESS_CODE.getValue());
    }

}
