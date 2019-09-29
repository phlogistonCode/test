package com.appricot.test.main

import com.appricot.test.base.BasePresenter
import com.appricot.test.base.BaseView

interface MainContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter<View>
}