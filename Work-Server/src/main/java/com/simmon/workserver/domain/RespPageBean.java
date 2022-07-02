package com.simmon.workserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * <p>公共分页返回对象domain类</p>
 * @author Simmon
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {
    private Long total;
    private List<?> data;
}
