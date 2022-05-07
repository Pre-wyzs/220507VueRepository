import CryptoJS from "crypto-js";

/**
 * 
 * CtyptoJS加密
 */
export function getAES(data){  //加密
    const key = CryptoJS.enc.Utf8.parse("1985718124500000");//加密秘钥
    let encrypted = '';  //加密后的接收变量
    const srcs = CryptoJS.enc.Utf8.parse(data);
    encrypted = CryptoJS.AES.encrypt(srcs,key,{
        mode:CryptoJS.mode.ECB,
        padding:CryptoJS.pad.Pkcs7
    });
    console.log(encrypted);
    return encrypted.toString();
}

/**
 * CryptoJS解密
 * 
 * @param {*} data 
 * @returns 
 */
export function getDAES(data){  
    const key = CryptoJS.enc.Utf8.parse("1985718124500000");//加密秘钥
    const decrypted = CryptoJS.AES.decrypt(data,key,{
        mode:CryptoJS.mode.ECB,
        padding:CryptoJS.pad.Pkcs7
    });
    console.log(decrypted);
    return CryptoJS.enc.Utf8.stringify(decrypted).toString();
}
