package com.yangy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionDTOs {
    private List<UserPermissionDTO> userPermissionDTOList;
    private Integer total;
}
