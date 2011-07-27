package com.m3958.firstgwt.utils;

import org.junit.Assert;
import org.junit.Test;

import com.m3958.firstgwt.client.types.MenuItemCategory;

public class EnumTttt {
	
	@Test
	public void tt(){
		
		Assert.assertTrue(MenuItemCategory.BASE instanceof Enum<?>);
		
		Assert.assertEquals(Enum.valueOf(MenuItemCategory.class, "BASE"),MenuItemCategory.OA);
		
	}

}
