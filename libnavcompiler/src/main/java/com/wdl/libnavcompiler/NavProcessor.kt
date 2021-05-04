package com.wdl.libnavcompiler

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.google.auto.service.AutoService
import com.wdl.libnavannotation.ActivityDestination
import com.wdl.libnavannotation.FragmentDestination
import java.io.File
import java.lang.Exception
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(
    "com.wdl.libnavannotation.ActivityDestination",
    "com.wdl.libnavannotation.FragmentDestination"
)
class NavProcessor : AbstractProcessor() {
    private var mMessager: Messager? = null
    private var mFiler: Filer? = null
    private val OUTPUT_FILE_NAME = "destination.json"

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        processingEnvironment?.apply {
            mMessager = messager
            mFiler = filer
        }
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {
        // 获取自定义注解元素的集合
        val fragAnnotations =
            roundEnvironment?.getElementsAnnotatedWith(FragmentDestination::class.java)
        val activityAnnotations =
            roundEnvironment?.getElementsAnnotatedWith(ActivityDestination::class.java)

        if (!fragAnnotations.isNullOrEmpty() || !activityAnnotations.isNullOrEmpty()) {
            val destMap = HashMap<String, JSONObject>()
            handleDestination(fragAnnotations, FragmentDestination::class.java, destMap)
            handleDestination(activityAnnotations, ActivityDestination::class.java, destMap)

            generalOutput(destMap)

        }
        return true
    }

    private fun generalOutput(destMap: HashMap<String, JSONObject>) {
        try {
            // 生成文件-app/src/main/assets
            val fileObject =
                mFiler?.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME)
            val resourcePath = fileObject?.toUri()?.path
            mMessager?.printMessage(Diagnostic.Kind.NOTE, "resourcePath:$resourcePath")
            println("resourcePath:$resourcePath")
            val appPath = resourcePath?.substring(0, resourcePath.indexOf("app") + 4)
            val assetsPath = "${appPath}src/main/assets/"
            mMessager?.printMessage(Diagnostic.Kind.NOTE, "assetsPath:$assetsPath")
            val file = File(assetsPath)
            if (!file.exists()) {
                file.mkdirs()
            }

            val outputFile = File(file, OUTPUT_FILE_NAME)
            if (outputFile.exists()) {
                outputFile.delete()
            }
            outputFile.createNewFile()

            outputFile.writeText(JSON.toJSONString(destMap))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleDestination(
        annotations: Set<Element>?,
        annotationName: Class<out Annotation>,
        destMap: HashMap<String, JSONObject>
    ) {
        if (annotations.isNullOrEmpty()) return
        annotations.forEach {
            val typeElement: TypeElement = it as TypeElement
            val clazzName: String = typeElement.qualifiedName.toString()

            val id: Int = kotlin.math.abs(clazzName.hashCode())
            var pageUrl: String? = null
            var needLogin = false
            var asStarter = false
            var isFrag = false

            when (val annotation = typeElement.getAnnotation(annotationName)) {
                is FragmentDestination -> {
                    pageUrl = annotation.pageUrl
                    needLogin = annotation.needLogin
                    asStarter = annotation.asStarter
                    isFrag = true
                }

                is ActivityDestination -> {
                    pageUrl = annotation.pageUrl
                    needLogin = annotation.needLogin
                    asStarter = annotation.asStarter
                    isFrag = false
                }
            }

            if (destMap.containsKey(pageUrl)) {
                mMessager?.printMessage(Diagnostic.Kind.ERROR, "pageUrl must be unique:$clazzName")
            } else {
                val jsonObject = JSONObject().apply {
                    put("id", id)
                    put("pageUrl", pageUrl)
                    put("needLogin", needLogin)
                    put("asStarter", asStarter)
                    put("isFrag", isFrag)
                    put("clazzName", clazzName)
                }

                pageUrl?.apply { destMap[this] = jsonObject }
            }

        }
    }
}