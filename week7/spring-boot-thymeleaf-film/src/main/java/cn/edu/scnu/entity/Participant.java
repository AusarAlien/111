package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("participant")
public class Participant {

    @TableId(type= IdType.AUTO)
    private Long participantId;
    private String name;
    private Boolean isDirector;
}
