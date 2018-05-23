
function changeTab(tabCon_num){
    for(i=0;i<=5;i++) {
        document.getElementById("tabCon_"+i).style.display="none"; //将所有的层都隐藏
    }
    document.getElementById("tabCon_"+tabCon_num).style.display="block";//显示当前层
}