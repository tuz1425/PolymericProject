package com.test.aggregatepayment.utils

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/28
 */
class Parameter {
    companion object {
        /** 登录失败 */
        const val GOOGLE_PAY_ERROR = 2001
        const val REGISTER_ERROR = 3001
        const val REGISTER_CANCEL = 3002
        const val SIGN_IN_ERROR = 3003
        /** 删除成功 */
        const val DELETE_USER_SUCCESS = 4001
        /** 删除失败 */
        const val DELETE_USER_ERROR = 4002
        /** 异常操作 */
        const val ABNORMAL_OPERATION = 5000
    }
}

class RequestParameter {
    companion object {
        /** activity 请求码 */
        const val SIGN_IN_CODE = 3001
    }
}