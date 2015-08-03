/**
 *  Copyright (c) by Shanghai PoleLink Information Technology Co.,Ltd. All rights reserved.
 *
 *  This software is copyright protected and proprietary to Shanghai PoleLink
 *  Information Technology. Shanghai PoleLink Information Technology Co.,Ltd
 *  grants to you only those rights as set out in the license conditions.
 *  All other rights remain with Shanghai PoleLink Information Technology Co.,Ltd.
 **/

package com.polelink.Icon.Internal;


public interface ConstantsCommon {

	public static final String PIKE_APPLICATION_NAME = "pike";
	public static final String PIKE_CORE_MODULE_NAME = "pike.pike-core";

	public static final boolean PRODUCT_MODE = false;

	public static final String MODULE_ACTIVE_STATUS = "ACTIVE";



	public static final String DEPLOY_IDENTITY = "deploy-identity-";// 这个值是为了给部署模块时查找DBService时的唯一标识

	public final static int MATCH_ALL = 0;
	public final static int MATCH_ANY = 1;
	public final static int NOT_MATCH_ANY = 2;

	/**
	 * The location of the exception message length
	 */
	public static final String LOCATION_CENTER = "C";
	public static final String LOCATION_LEFT = "L";
	public static final String LOCATION_RIGHT = "R";

	// mail address type
	public static final String MAIL_TO = "To";
	public static final String MAIL_FROM = "From";
	public static final String MAIL_CC = "Cc";
	public static final String MAIL_BCC = "Bcc";
	public static final String MAIL_BOUNCE = "Bounce";

	// language
	public static String LANGUAGE_EN = "en";
	public static String LANGUAGE_ZH = "zh";

	// Server Name
	public static final String SERVER_NAME_EMAIL = "EMAIL";
	public static final String SERVER_NAME_APP = "APP";

	// System Parameter
	public static final String PARAMETER_DEFAULT_LANGUAGE = "DEFAULT_LANGUAGE";
	public static final String PARAMETER_SESSION_TIMEOUT = "SESSION_TIMEOUT";
	public static final String PARAMETER_COMPANY = "COMPANY";
	public static final String PARAMETER_TITLE = "TITLE";

	/**
	 * The split char of the nls properties key name
	 */
	public static final String CHAR_PRO_SPLIT = ".";

	public static final String FILTER_VALUE_SPILIT = ";";

	/**
	 * The split char of the exception fill char
	 */
	public static final String CHAR_EXCEPTION_FILL_MSG = "++++";

	/**
	 * The split char of the service property value divide char
	 */
	public static final String CHAR_SERVICE_PROPERTY_SPLIT = ";\n";

	/**
	 * The length of the exception message length
	 */
	public static final int LENGTH_EXCEPTION_FILL_MSG = 80;

	public static final String JMS_TYPE_TOPIC = "Topic";
	public static final String JMS_TYPE_QUEUE = "Queue";

	public static final String ENABLED_YES = "Y";
	public static final String ENABLED_YES_DESC = "Yes";
	public static final String ENABLED_NO = "N";
	public static final String ENABLED_NO_DESC = "No";

	// 以下是所有的操作符号
	public static final String EXPRESSION_EQUAL = "eq";
	public static final String EXPRESSION_LIKE = "like";
	public static final String EXPRESSION_NOT_LIKE = "not like";
	public static final String EXPRESSION_GREATER = "gt";
	public static final String EXPRESSION_LESS = "lt";
	public static final String EXPRESSION_BETWEEN = "between";
	public static final String EXPRESSION_IN = "in";
	public static final String EXPRESSION_IS_NULL = "null";
	public static final String EXPRESSION_NOT_NULL = "not null";

	// Admin User & Roles
	public static final String USER_ADMIN = "Admin";
	public static final String ROLE_ADMIN = "Administrator";

}
