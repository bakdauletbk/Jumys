package kz.smartideagroup.jumys.common.remote

object EndPoints {

    const val POST_AUTH_SMS = "/v1/auth/sms"
    const val POST_AUTH_VERIFICATION = "/v1/auth/verification"
    const val POST_SIGN_IN = "/v1/auth/sign-in"
    const val POST_SIGN_UP_MANAGER = "/v1/auth/sign-up-client"
    const val POST_SIGN_UP_CLIENT = "/v1/auth/sign-up-master"
    const val GET_WORK = "/v1/works?WorkSearch[id]=10"
    const val GET_WORK_ID = "/v1/works"
    const val POST_WORK_CREATE = "/v1/works"
    const val GET_WORK_ID_TWO = "/v1/works/2"
    const val GET_STORIES = "/v1/stories"
    const val GET_CATEGORIES = "/v1/categories"
    const val GET_ABOUT_CONTACT = "/v1/about/contact"
    const val POST_WORK_UPLOAD = "/v1/work/upload-video"
}