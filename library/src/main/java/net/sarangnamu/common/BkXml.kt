/*
 * Copyright (C) Hanwha S&C Ltd., 2017. All rights reserved.
 *
 * This software is covered by the license agreement between
 * the end user and Hanwha S&C Ltd., and may be
 * used and copied only in accordance with the terms of the
 * said agreement.
 *
 * Hanwha S&C Ltd., assumes no responsibility or
 * liability for any errors or inaccuracies in this software,
 * or any consequential, incidental or indirect damage arising
 * out of the use of the software.
 */

package net.sarangnamu.common

import org.slf4j.LoggerFactory
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStream
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 *
 *  class TestParser : XPathBase() {
        override fun parsing() {
            val count  = int("count(//xsync)")
            val number = string("//rgist/text()")

            if (TextUtils.isEmpty(number)) {
                val msg = string("//message/text()")
                val err = string("//error_code/text()")
            } else {
                var i=0
                while (i++ <count) {
                    // TODO
                }
            }
        }
    }
 */
abstract class XPathBase {
    companion object {
        private val log = LoggerFactory.getLogger(XPathBase::class.java)
    }

    val xpath   = XPathFactory.newInstance().newXPath()
    val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    lateinit var document: Document

    /**
     * xml 파일 핸을 연다
     */
    fun loadXml(fp: File) {
        if (!fp.exists()) {
            log.error("ERROR: FILE NOT FOUND ($fp)")
            return
        }

        try {
            document = builder.parse(fp)
            parsing()
        } catch (e: Exception) {
            log.error("ERROR: ${e.printStackTrace()}")
        }
    }

    /**
     * xml input stream 을 연다
     */
    fun loadXml(ism: InputStream) {
        try {
            document = builder.parse(ism)
            parsing()
        } catch (e: Exception) {
            log.error("ERROR: ${e.printStackTrace()}")
        }
    }

    /**
     * xml string 로드 한다
     */
    fun loadXml(xml: String) {
        try {
            document = builder.parse(InputSource(StringReader(xml)))
            parsing()
        } catch (e: Exception) {
            log.error("ERROR: ${e.printStackTrace()}")
        }
    }

    /** string 으로 반환 */
    fun string(expr: String) = xpath.evaluate(expr, document, XPathConstants.STRING).toString().trim()
    /** int 로 반환 */
    fun int(expr: String)    = string(expr).toInt()
    /** float 으로 반환 */
    fun float(expr: String)  = string(expr).toFloat()
    /** double 로 반환 */
    fun double(expr: String) = string(expr).toDouble()
    /** bool 로 반환 */
    fun bool(expr: String)   = string(expr).toBoolean()

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // ABSTRACT
    //
    ////////////////////////////////////////////////////////////////////////////////////

    abstract fun parsing()
}