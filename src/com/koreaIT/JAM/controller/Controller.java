package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Member;

public abstract class Controller {
	public Scanner sc;
	public String cmd;
	public static Member loginedMember;
	public Connection connection;

	public abstract void doAction(String cmd, String methodName);

	public static boolean isLogined() {
		
		return false;
	}
	
}
