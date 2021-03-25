package com.zdata.constant;

/**
 * 静态常量枚举类
 * @author Administrator
 *
 */
public enum Constant {
	
	Status_Name("status"),
	SUCCESS("success"),
	FAIL("fail"),
	Data_Name("data"),
	Row_Name("row"),
	Rows_Name("rows"),
	Total_Name("total"),
	Sum_Name("sum"),
	Id_Name("id"),
	Result_Msg("msg"),
	Current_User("curUser"),
	Current_User_Id("curUserId"),
	Current_Soft("curSoft"),
	Admin_Password("admin2020"),
	Flow_Password("21218cca77804d2ba1922c33e0151105"),
	Session_Timeout("1800"),
	;
	
	private final String value;
	Constant(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
