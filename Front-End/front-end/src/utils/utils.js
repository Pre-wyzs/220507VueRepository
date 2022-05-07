//一个Js导出多个函数
//把空字符串转换成null的方法
function stringToNull(str){
    if(str == ''){
        return null
    }
    return str;
} //

function dateFormat(date){
    let ret = date;
    if(ret  == '' || ret == null){
        return null;
    }
    return ret.replaceAll('T',' ').concat(':00');  //使用concat会改变原来的date的对象的，但是原来的不能该，改的话就和原来的控件值匹配不上了
}
    

//这里就不能加上default了
export {
    stringToNull,
    dateFormat
};
