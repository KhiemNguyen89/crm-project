package com.spring.myproject.crm.constant;

public class ApplicationConstant {

	public class TaskStatus {
		public static final int NOT_STARTED = 1;
		public static final int IN_PROGRESS = 2;
		public static final int COMPLETED = 3;
	}

	public class RoleId {
		public static final int ADMIN = 1;
		public static final int MANAGER = 2;
		public static final int MEMBER = 3;
	}

	public static final String SECRET_KEY = "404E635266556A586E3272357538782F4125442A472D4B6150645367566B5970";
	public static final String JWT_HEADER = "Authorization";
}
