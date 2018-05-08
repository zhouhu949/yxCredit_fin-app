package com.zw.rule.customer.po;

public class Order {
    private String id;

    private String orderNo;//订单编号

    private String contract_id;

    private String userId;//用户ID

    private String periods;//分期期数 申请期限

    private String merchantId;//商户ID

    private String merchantName;//商户名称

    private String merchandiseId;//商品ID

    private String merchandiseType;//商品类型

    private String merchandiseName;//商品名称

    private String customerId;//客户ID

    private String customerName;//客户名

    private String sexName;//客户性别


    private String tel;//客户联系电话

    private String cardType;//证件类型

    private String card;//证件号码

    private String credit;//授信额度

    private String precredit;//预授信额度

    private String amount;//商品金额

    private String company;//归属公司

    private String branch;//归属部门

    private String empId;//员工ID

    private String manager;//归属人 当前审批人

    private String creatTime;//创建时间

    private String alterTime;//修改时间

    private String state;//状态

    private String apex1;

    private String apex2;

    private String apex3;

    private String bak;

    private String repayType;//还款方式

    private String rate;//利率（年%）


    private String cusSource;//客户来源

    private String merchandiseBrand;//商品品牌

    private String merchandiseModel;//商品型号

    private String familyLinkmanState;//家庭联系人完成进度

    private String otherLinkmanState;//其他联系人完成进度

    private String merchandiseCode;//商品代码

    private String provincesId;

    private String cityId;

    private String districId;

    private String provinces;

    private String city;

    private String distric;

    private String productType;//产品系列编号

    private String productTypeName; //产品类型

    private String productName;//产品名称编号

    private String productNameName;//产品名称名称

    private String productDetail;//产品期数编号

    private String productDetailName;//产品期数名称

    private String productDetailCode;//第三级产品code


    private String contractAmount;//合同总价

    private String loanAmount;//放款金额

    private String sex;

    private String repayTypeId;

    private String levelId;

    private String cusSourceId;

    private String merchandiseBrandId;

    private String merchandiseTypeId;

    private String empNumber;//员工工号

    private String crmOrderId;

    private String repayMoney;//ux月还款金额

    private String orderType;//实物分期是1，u信分期是2

    private String applayMoney;//申请金额

    private String predictPrice;//预估装修金额

    private String receiveId;//领取人id

    private String scoreCard;//评分卡json字符串

    private String photoState;//拍客状态

    private String orderSubmissionTime;//订单提交时间

    private String sumLoanRepay;//还款总金额

    private String remark;//备注

    private String feeState;//费用状态

    private String manageFee;//平台管理费

    private String fee;//利息

    private String quickTrialFee;//快审费

    private String taskNodeId;//任务节点
    private String repayDate;
    private String requestno;//易宝扣款批次号
    private String valRequestno;//易宝扣款批次号
    private String source;//客户来源
    private String weight;//黄金重量
    private String price;//黄金单价
    private String deliveryState;//发货状态
    private String rebatesState;//回购状态
    private String merName;//回购商户名称
    private String answerState;
    private String answerScore;
    private String repaymentState;
    private String loanState;

    private String applyFenQiLimit;//申请额度

    private String  commodityState;

    private String  reclaimAmount;//回购金额

    public String getReclaimAmount() {
        return reclaimAmount;
    }

    public void setReclaimAmount(String reclaimAmount) {
        this.reclaimAmount = reclaimAmount;
    }

    public String getLoanState() {
        return loanState;
    }

    public void setLoanState(String loanState) {
        this.loanState = loanState;
    }

    public String getCommodityState() {
        return commodityState;
    }

    public void setCommodityState(String commodityState) {
        this.commodityState = commodityState;
    }

    private String relId;//关联表id 担保人和订单关联表
    private String relState;//担保人和订单关联表的状态(关联状态 0-停用 1-待审核 2-已审核)
    private String checkResult;//审核结果
    private String approveSuggestion;//审核备注
    private String checkManName;//审核人姓名
    private String checkManId;//审核人id
    private String checkId;//审批意见表id

    private String diyType;// 自定义类型
    private String diyDays;// 自定义天数
    private String offlineOrder;// 订单类型

    public String getDiyType() {
        return diyType;
    }

    public void setDiyType(String diyType) {
        this.diyType = diyType;
    }

    public String getDiyDays() {
        return diyDays;
    }

    public void setDiyDays(String diyDays) {
        this.diyDays = diyDays;
    }

    public String getOfflineOrder() {
        return offlineOrder;
    }

    public void setOfflineOrder(String offlineOrder) {
        this.offlineOrder = offlineOrder;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCheckManName() {
        return checkManName;
    }

    public void setCheckManName(String checkManName) {
        this.checkManName = checkManName;
    }

    public String getCheckManId() {
        return checkManId;
    }

    public void setCheckManId(String checkManId) {
        this.checkManId = checkManId;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getApproveSuggestion() {
        return approveSuggestion;
    }

    public void setApproveSuggestion(String approveSuggestion) {
        this.approveSuggestion = approveSuggestion;
    }

    public String getRelState() {
        return relState;
    }

    public void setRelState(String relState) {
        this.relState = relState;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getApplyFenQiLimit() {
        return applyFenQiLimit;
    }

    public void setApplyFenQiLimit(String applyFenQiLimit) {
        this.applyFenQiLimit = applyFenQiLimit;
    }

    public String getRepaymentState() {
        return repaymentState;
    }

    public void setRepaymentState(String repaymentState) {
        this.repaymentState = repaymentState;
    }

    public String getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(String answerScore) {
        this.answerScore = answerScore;
    }

    public String getAnswerState() {
        return answerState;
    }

    public void setAnswerState(String answerState) {
        this.answerState = answerState;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getRebatesState() {
        return rebatesState;
    }

    public void setRebatesState(String rebatesState) {
        this.rebatesState = rebatesState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(String merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public String getMerchandiseType() {
        return merchandiseType;
    }

    public void setMerchandiseType(String merchandiseType) {
        this.merchandiseType = merchandiseType;
    }

    public String getMerchandiseName() {
        return merchandiseName;
    }

    public void setMerchandiseName(String merchandiseName) {
        this.merchandiseName = merchandiseName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getPrecredit() {
        return precredit;
    }

    public void setPrecredit(String precredit) {
        this.precredit = precredit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(String alterTime) {
        this.alterTime = alterTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApex1() {
        return apex1;
    }

    public void setApex1(String apex1) {
        this.apex1 = apex1;
    }

    public String getApex2() {
        return apex2;
    }

    public void setApex2(String apex2) {
        this.apex2 = apex2;
    }

    public String getApex3() {
        return apex3;
    }

    public void setApex3(String apex3) {
        this.apex3 = apex3;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCusSource() {
        return cusSource;
    }

    public void setCusSource(String cusSource) {
        this.cusSource = cusSource;
    }

    public String getMerchandiseBrand() {
        return merchandiseBrand;
    }

    public void setMerchandiseBrand(String merchandiseBrand) {
        this.merchandiseBrand = merchandiseBrand;
    }

    public String getMerchandiseModel() {
        return merchandiseModel;
    }

    public void setMerchandiseModel(String merchandiseModel) {
        this.merchandiseModel = merchandiseModel;
    }

    public String getFamilyLinkmanState() {
        return familyLinkmanState;
    }

    public void setFamilyLinkmanState(String familyLinkmanState) {
        this.familyLinkmanState = familyLinkmanState;
    }

    public String getOtherLinkmanState() {
        return otherLinkmanState;
    }

    public void setOtherLinkmanState(String otherLinkmanState) {
        this.otherLinkmanState = otherLinkmanState;
    }

    public String getMerchandiseCode() {
        return merchandiseCode;
    }

    public void setMerchandiseCode(String merchandiseCode) {
        this.merchandiseCode = merchandiseCode;
    }

    public String getProvincesId() {
        return provincesId;
    }

    public void setProvincesId(String provincesId) {
        this.provincesId = provincesId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistricId() {
        return districId;
    }

    public void setDistricId(String districId) {
        this.districId = districId;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNameName() {
        return productNameName;
    }

    public void setProductNameName(String productNameName) {
        this.productNameName = productNameName;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductDetailName() {
        return productDetailName;
    }

    public void setProductDetailName(String productDetailName) {
        this.productDetailName = productDetailName;
    }

    public String getProductDetailCode() {
        return productDetailCode;
    }

    public void setProductDetailCode(String productDetailCode) {
        this.productDetailCode = productDetailCode;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRepayTypeId() {
        return repayTypeId;
    }

    public void setRepayTypeId(String repayTypeId) {
        this.repayTypeId = repayTypeId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getCusSourceId() {
        return cusSourceId;
    }

    public void setCusSourceId(String cusSourceId) {
        this.cusSourceId = cusSourceId;
    }

    public String getMerchandiseBrandId() {
        return merchandiseBrandId;
    }

    public void setMerchandiseBrandId(String merchandiseBrandId) {
        this.merchandiseBrandId = merchandiseBrandId;
    }

    public String getMerchandiseTypeId() {
        return merchandiseTypeId;
    }

    public void setMerchandiseTypeId(String merchandiseTypeId) {
        this.merchandiseTypeId = merchandiseTypeId;
    }

    public String getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    public String getCrmOrderId() {
        return crmOrderId;
    }

    public void setCrmOrderId(String crmOrderId) {
        this.crmOrderId = crmOrderId;
    }

    public String getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(String repayMoney) {
        this.repayMoney = repayMoney;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getApplayMoney() {
        return applayMoney;
    }

    public void setApplayMoney(String applayMoney) {
        this.applayMoney = applayMoney;
    }

    public String getPredictPrice() {
        return predictPrice;
    }

    public void setPredictPrice(String predictPrice) {
        this.predictPrice = predictPrice;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(String scoreCard) {
        this.scoreCard = scoreCard;
    }

    public String getPhotoState() {
        return photoState;
    }

    public void setPhotoState(String photoState) {
        this.photoState = photoState;
    }

    public String getOrderSubmissionTime() {
        return orderSubmissionTime;
    }

    public void setOrderSubmissionTime(String orderSubmissionTime) {
        this.orderSubmissionTime = orderSubmissionTime;
    }

    public String getSumLoanRepay() {
        return sumLoanRepay;
    }

    public void setSumLoanRepay(String sumLoanRepay) {
        this.sumLoanRepay = sumLoanRepay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFeeState() {
        return feeState;
    }

    public void setFeeState(String feeState) {
        this.feeState = feeState;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getQuickTrialFee() {
        return quickTrialFee;
    }

    public void setQuickTrialFee(String quickTrialFee) {
        this.quickTrialFee = quickTrialFee;
    }

    public String getTaskNodeId() {
        return taskNodeId;
    }

    public void setTaskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getValRequestno() {
        return valRequestno;
    }

    public void setValRequestno(String valRequestno) {
        this.valRequestno = valRequestno;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", periods='" + periods + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchandiseName='" + merchandiseName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", sexName='" + sexName + '\'' +
                ", tel='" + tel + '\'' +
                ", card='" + card + '\'' +
                ", precredit='" + precredit + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}