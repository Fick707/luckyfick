package com.fick.luckyfick.dao.http.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fick.common.utils.http.HttpClientResult;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.BetHistoryDTO;
import com.fick.luckyfick.utils.HistoryBetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @name: BetHistoryResponseHandler
 * @program: gina-vehicle
 * @description:
 * @author: figo.song
 * @created: 2019/12/26
 **/
@Component
@Slf4j
public class BetHistoryResponseHandler implements ResponseHandler<HttpClientResult<BetHistoryDTO>> {
    @Override
    public HttpClientResult<BetHistoryDTO> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        HttpClientResult<BetHistoryDTO> retV = new HttpClientResult<>();
        BetHistoryDTO betHistoryDTO = new BetHistoryDTO();
        StatusLine statusLine = response.getStatusLine();
        HttpEntity httpEntity = response.getEntity();
        if (statusLine.getStatusCode() >= 300 || statusLine.getStatusCode() < 200) {
            retV.setCode(statusLine.getStatusCode());
            retV.setMessage(statusLine.getReasonPhrase());
            return retV;
        }
        if (null == httpEntity) {
            retV.setCode(statusLine.getStatusCode());
            retV.setMessage("response no content.");
            return retV;
        }
        String content = EntityUtils.toString(httpEntity);
        log.debug("fetch bet history http response : {}.",content);
        if(StringUtils.isNotBlank(content)){
            JSONObject jsonObject = JSON.parseObject(content);
            if(jsonObject != null){
                JSONArray result = jsonObject.getJSONArray("result");
                if(result != null && !result.isEmpty()){
                    List<Bet> bets = new ArrayList<>();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    for(int i = 0 ; i < result.size() ; i ++){
                        JSONObject betHis = result.getJSONObject(i);
                        Bet bet = new Bet();
                        bet.setCode(Integer.parseInt(betHis.getString("code")));
                        bet.setBlue1(Integer.parseInt(betHis.getString("blue")));
                        String[] reds = betHis.getString("red").split(",");
                        bet.setRed1(Integer.parseInt(reds[0]));
                        bet.setRed2(Integer.parseInt(reds[1]));
                        bet.setRed3(Integer.parseInt(reds[2]));
                        bet.setRed4(Integer.parseInt(reds[3]));
                        bet.setRed5(Integer.parseInt(reds[4]));
                        bet.setRed6(Integer.parseInt(reds[5]));
                        bet.setWeek(HistoryBetUtils.getWeek(betHis.getString("week")));
                        String date = betHis.getString("date");
                        date = StringUtils.substringBefore(date,"(");
                        try {
                            bet.setDate(sdf.parse(date).getTime());
                        } catch (ParseException e) {
                            log.error("parse date error {}.",betHis.toJSONString());
                        }
                        bets.add(bet);
                    }
                    betHistoryDTO.setBets(bets);
                    retV.setData(betHistoryDTO);
                }
            }
        }
        retV.setMessage("success");
        retV.setCode(statusLine.getStatusCode());
        return retV;
    }
}
