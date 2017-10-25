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

import android.text.TextUtils
import org.slf4j.LoggerFactory
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStream
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPath
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
public abstract class XPathBase {
    private val log = LoggerFactory.getLogger(XPathBase::class.java)

    val xpath   = XPathFactory.newInstance().newXPath()
    val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    lateinit var document: Document

    open fun loadXml(fp: File) {
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

    open fun loadXml(ism: InputStream) {
        try {
            document = builder.parse(ism)
            parsing()
        } catch (e: Exception) {
            log.error("ERROR: ${e.printStackTrace()}")
        }
    }

    open fun loadXml(xml: String) {
        try {
            document = builder.parse(InputSource(StringReader(xml)))
            parsing()
        } catch (e: Exception) {
            log.error("ERROR: ${e.printStackTrace()}")
        }
    }

    fun string(expr: String) = xpath.evaluate(expr, document, XPathConstants.STRING).toString().trim()
    fun int(expr: String)    = string(expr).toInt()
    fun float(expr: String)  = string(expr).toFloat()
    fun double(expr: String) = string(expr).toDouble()
    fun bool(expr: String)   = string(expr).toBoolean()

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // ABSTRACT
    //
    ////////////////////////////////////////////////////////////////////////////////////

    abstract fun parsing()
}
