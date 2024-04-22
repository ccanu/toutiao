package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;

import lombok.Data;

@Data
public class WmMaterialDto extends PageRequestDto {

	
	/**
	 * 1 気に入り
	 * 2. 気に入りでない
	 */
	
	private Short isCollection;
	
}
