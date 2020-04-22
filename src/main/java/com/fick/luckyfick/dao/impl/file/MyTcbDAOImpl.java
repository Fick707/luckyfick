package com.fick.luckyfick.dao.impl.file;

import com.fick.common.utils.file.AbsoluteFileReader;
import com.fick.common.utils.file.CommonFileWriter;
import com.fick.luckyfick.dao.MyTcbDAO;
import com.fick.luckyfick.model.MyBet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/22
 **/
@Component
@Slf4j
public class MyTcbDAOImpl implements MyTcbDAO {

    @Value("${com.fick.luckyfick.tcb.mine.file}")
    private String myTcbFileName;

    @Override
    public List<MyBet> loadMyHistory() {
        log.info("load my bet history from file");
        AbsoluteFileReader commonFileReader = new AbsoluteFileReader(myTcbFileName,"#",0,null);
        List<String> lines = commonFileReader.readLines();
        if(CollectionUtils.isNotEmpty(lines)){
            List<MyBet> his = new ArrayList<>();
            for(String line : lines){
                String[] vs = line.split(",");
                MyBet bet = new MyBet();
                bet.setIndex(Integer.parseInt(vs[0]));
                bet.setCode(Integer.parseInt(vs[1]));
                bet.setDate(Long.parseLong(vs[2]));
                bet.setRed1(Integer.parseInt(vs[3]));
                bet.setRed2(Integer.parseInt(vs[4]));
                bet.setRed3(Integer.parseInt(vs[5]));
                bet.setRed4(Integer.parseInt(vs[6]));
                bet.setRed5(Integer.parseInt(vs[7]));
                bet.setRed6(Integer.parseInt(vs[8]));
                bet.setBlue1(Integer.parseInt(vs[9]));
                bet.setMultiple(Integer.parseInt(vs[10]));
                his.add(bet);
            }
            return his;
        }
        return new ArrayList<>();
    }

    @Override
    public Integer addMyBet(MyBet bet) {
        bet.setIndex(generateIndex());
        CommonFileWriter fileWriter = new CommonFileWriter(myTcbFileName);
        fileWriter.append(String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",
                bet.getIndex(),
                bet.getCode(),
                bet.getDate(),
                bet.getRed1(),
                bet.getRed2(),
                bet.getRed3(),
                bet.getRed4(),
                bet.getRed5(),
                bet.getRed6(),
                bet.getBlue1(),
                bet.getMultiple()
        ));
        return bet.getIndex();
    }


    /**
     * 从历史记录中获取最后一条，用于id自增
     * @return
     */
    private MyBet getLastBet() {
        List<MyBet> his = loadMyHistory();
        return his.get(his.size() - 1);
    }

    /**
     * 根据历史记录，生成自增id
     * @return
     */
    private Integer generateIndex() {
        return getLastBet().getIndex() + 1;
    }
}
