package com.wdl.ppjoke.utils

import android.content.ComponentName
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import com.wdl.ppjoke.nav.FixFragmentNavigator

object NavGraphBuilder {
    fun build(controller: NavController) {
        val navigatorProvider = controller.navigatorProvider
        // 获取fragment Nav
        val fragNavigator = navigatorProvider.getNavigator(FixFragmentNavigator::class.java)
        // 获取activity Nav
        val activityNavigator = navigatorProvider.getNavigator(ActivityNavigator::class.java)
        // 创建nav
        val navGraph = NavGraph(NavGraphNavigator(navigatorProvider))
        // 配置文件获取节点
        val destinationConfig = AppConfig.getDestinationConfig()
        if (destinationConfig.isNullOrEmpty()) return
        destinationConfig.values.forEach {
            // 是启动页-id相关联
            if (it.asStarter) {
                navGraph.startDestination = it.id
            }
            // 根据是否是frag标记添加对应的节点
            navGraph.addDestination(
                when (it.isFrag) {
                    true -> fragNavigator.createDestination().apply {
                        className = it.clazzName
                        id = it.id
                        addDeepLink(it.pageUrl)
                    }

                    false -> activityNavigator.createDestination().apply {
                        setComponentName(AppGlobals.getApplication()?.packageName?.let { pkg ->
                            ComponentName(
                                pkg, it.clazzName
                            )
                        })
                        id = it.id
                        addDeepLink(it.pageUrl)
                    }
                }
            )
        }

        controller.graph = navGraph

    }
}