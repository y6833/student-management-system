package com.yangy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRankExamDTO {
    private String examName;
    private List<SubRankDTO> subRankDTO;

}
