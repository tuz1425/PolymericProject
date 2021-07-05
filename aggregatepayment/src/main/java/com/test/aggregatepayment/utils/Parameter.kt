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
        const val SIGN_IN_ERROR = 1001
        const val GOOGLE_PAY_ERROR = 2001
        const val FACE_BOOK_REGISTER_ERROR = 3001
        const val FACE_BOOK_REGISTER_CANCEL = 3002
        const val FACE_BOOK_SIGN_IN_ERROR = 3003
    }
}

class RequestParameter {
    companion object {
        /** activity 请求码 */
        const val SIGN_IN_CODE = 3001
    }
}