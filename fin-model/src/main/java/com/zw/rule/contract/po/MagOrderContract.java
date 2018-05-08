package com.zw.rule.contract.po;

import java.io.Serializable;
import java.util.Date;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年07月20日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class MagOrderContract implements Serializable {
    /**合同id**/
    private String id;
    /**客户id**/
    private String customerId;
    /**订单id**/
    private String orderId;
    /**状态**/
    private String state;
    /**创建时间**/
    private Date createTime;
    /**商户id**/
    private String merchant_id;

    /**商户名称**/
    private String merchant_name;
    private String customer_name;
    private Date alterTime;
    private String bak;

    private String contract_src;

    private String order_no;
    private String contract_amount;
    private String contract_name;
    private String manager;
    private String protocol_name;
    private String contract_no;
    private String month;
    private String effect_date;
    private String expire_date;
    private String interest;
    private String begin_date;
    private String end_date;
    private String day;
    private String bank;
    private String bank_subbranch;

    private String bank_city;
    private String account;
    private String account_name;
    private String loan_amount;
    private String emp_id;
    private String channel;
    private String amount;
    private String user_id;
    private String company;
    private String BRANCH;
    private String operator;
    private String applay_time;
    private String merchant_address;
    private String poscode;
    private String merchandise_type;
    private String merchandise_brand;
    private String merchandise_model;
    private String first_pay_money;
    private String contract_rate;
    private String month_service;
    private String interest_rate;
    private String befor_service_rate;
    private String emp_number;
    private String product_type;
    private String product_type_name;
    private String product_name;
    private String product_name_name;
    private String product_detail;
    private String product_detail_name;
    private String product_code;
    private String consult_rate;
    private String yearRate;
    private String custID;
    private String purposeName;
    private String payCapialCN;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Date getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(Date alterTime) {
        this.alterTime = alterTime;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getContract_amount() {
        return contract_amount;
    }

    public void setContract_amount(String contract_amount) {
        this.contract_amount = contract_amount;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getProtocol_name() {
        return protocol_name;
    }

    public void setProtocol_name(String protocol_name) {
        this.protocol_name = protocol_name;
    }

    public String getContract_no() {
        return contract_no;
    }

    public void setContract_no(String contract_no) {
        this.contract_no = contract_no;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEffect_date() {
        return effect_date;
    }

    public void setEffect_date(String effect_date) {
        this.effect_date = effect_date;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_subbranch() {
        return bank_subbranch;
    }

    public void setBank_subbranch(String bank_subbranch) {
        this.bank_subbranch = bank_subbranch;
    }

    public String getBank_city() {
        return bank_city;
    }

    public void setBank_city(String bank_city) {
        this.bank_city = bank_city;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBRANCH() {
        return BRANCH;
    }

    public void setBRANCH(String BRANCH) {
        this.BRANCH = BRANCH;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getApplay_time() {
        return applay_time;
    }

    public void setApplay_time(String applay_time) {
        this.applay_time = applay_time;
    }

    public String getMerchant_address() {
        return merchant_address;
    }

    public void setMerchant_address(String merchant_address) {
        this.merchant_address = merchant_address;
    }

    public String getPoscode() {
        return poscode;
    }

    public void setPoscode(String poscode) {
        this.poscode = poscode;
    }

    public String getMerchandise_type() {
        return merchandise_type;
    }

    public void setMerchandise_type(String merchandise_type) {
        this.merchandise_type = merchandise_type;
    }

    public String getMerchandise_brand() {
        return merchandise_brand;
    }

    public void setMerchandise_brand(String merchandise_brand) {
        this.merchandise_brand = merchandise_brand;
    }

    public String getMerchandise_model() {
        return merchandise_model;
    }

    public void setMerchandise_model(String merchandise_model) {
        this.merchandise_model = merchandise_model;
    }

    public String getFirst_pay_money() {
        return first_pay_money;
    }

    public void setFirst_pay_money(String first_pay_money) {
        this.first_pay_money = first_pay_money;
    }

    public String getContract_rate() {
        return contract_rate;
    }

    public void setContract_rate(String contract_rate) {
        this.contract_rate = contract_rate;
    }

    public String getMonth_service() {
        return month_service;
    }

    public void setMonth_service(String month_service) {
        this.month_service = month_service;
    }

    public String getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(String interest_rate) {
        this.interest_rate = interest_rate;
    }

    public String getBefor_service_rate() {
        return befor_service_rate;
    }

    public void setBefor_service_rate(String befor_service_rate) {
        this.befor_service_rate = befor_service_rate;
    }

    public String getEmp_number() {
        return emp_number;
    }

    public void setEmp_number(String emp_number) {
        this.emp_number = emp_number;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_type_name() {
        return product_type_name;
    }

    public void setProduct_type_name(String product_type_name) {
        this.product_type_name = product_type_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_name_name() {
        return product_name_name;
    }

    public void setProduct_name_name(String product_name_name) {
        this.product_name_name = product_name_name;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }

    public String getProduct_detail_name() {
        return product_detail_name;
    }

    public void setProduct_detail_name(String product_detail_name) {
        this.product_detail_name = product_detail_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getConsult_rate() {
        return consult_rate;
    }

    public void setConsult_rate(String consult_rate) {
        this.consult_rate = consult_rate;
    }

    public String getYearRate() {
        return yearRate;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getPurposeName() {
        return purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public String getPayCapialCN() {
        return payCapialCN;
    }

    public void setPayCapialCN(String payCapialCN) {
        this.payCapialCN = payCapialCN;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContract_src() {
        return contract_src;
    }

    public void setContract_src(String contract_src) {
        this.contract_src = contract_src;
    }
}

