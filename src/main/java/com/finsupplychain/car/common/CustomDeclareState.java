package com.finsupplychain.car.common;

/**
 * @author jessy-js
 * @ClassName CustomDeclareWrapper
 * @Version V1.0
 * @Description
 */
public class CustomDeclareState {
    public static final String CREATED = "CF_N"; //已创建、
    public static final String UPDATED = "CF_U"; //已修改、
    public static final String SUBMITTED = "CF_S"; //已提交
    public static final String PRELIMINART_CHECKED = "CF_PR"; //已初审、
    public static final String REPEAT_CHECKED = "CF_R"; //已复审、
    public static final String SINGLE_WINDOW_SUBMITTED = "CF_SW"; //提交到海关单一窗口、
    public static final String SAVED = "CF_SS"; //暂存成功
    public static final String DIRECTLY_PASSED = "7"; //直接申报成功、
    public static final String WAREHOUSED = "L"; //入库成功!
    public static final String REQUEST_ACCEPTED = "J"; ////海关已接受申报、
    public static final String GUARANTEE_RELEASING = "K";//报关单担保放行
    public static final String PORT_INSPECTED = "C"; ////口岸检查、
    public static final String RELEASED = "3"; //已放行、
    public static final String CLOSED = "R"; //报关单已结关、
    public static final String DOCUMENT_RELEASED = "P"; //单证放行
}
