package cn.varsa.idea.pde.partial.plugin.provider

import cn.varsa.idea.pde.partial.plugin.openapi.provider.*
import java.io.*
import javax.xml.bind.*
import javax.xml.bind.annotation.*


/**
 *
 */

/*
<target name="jmc-target-2021-06" sequenceNumber="47">
    <locations>
        <location includeAllPlatforms="false" includeConfigurePhase="true" includeMode="planner" includeSource="true" type="InstallableUnit">
            <unit id="com.sun.mail.jakarta.mail" version="2.0.1"/>
            <unit id="com.sun.activation.jakarta.activation" version="2.0.1"/>
            <unit id="org.owasp.encoder" version="1.2.3"/>
            <unit id="org.lz4.lz4-java" version="1.8.0"/>
            <unit id="org.hdrhistogram.HdrHistogram" version="2.1.12"/>
            <unit id="org.adoptopenjdk.jemmy-awt-input" version="2.0.0"/>
            <unit id="org.adoptopenjdk.jemmy-browser" version="2.0.0"/>
            <unit id="org.adoptopenjdk.jemmy-core" version="2.0.0"/>
            <unit id="org.adoptopenjdk.jemmy-swt" version="2.0.0"/>
            <unit id="org.eclipse.jetty.servlet-api" version="4.0.6"/>
            <unit id="org.eclipse.jetty.websocket.api" version="10.0.5"/>
            <unit id="org.eclipse.jetty.websocket.server" version="10.0.5"/>
            <unit id="org.eclipse.jetty.websocket.servlet" version="10.0.5"/>
            <unit id="org.eclipse.jetty.websocket.javax.server" version="10.0.5"/>
            <unit id="org.apache.aries.spifly.dynamic.bundle" version="1.3.4"/>
            <repository location="http://localhost:8080/site"/>
        </location>
        <location includeAllPlatforms="false" includeConfigurePhase="true" includeMode="planner" includeSource="true" type="InstallableUnit">
            <unit id="org.eclipse.equinox.executable.feature.group" version="3.8.1200.v20210527-0259"/>
            <unit id="org.eclipse.pde.feature.group" version="3.14.800.v20210611-1636"/>
            <unit id="org.eclipse.platform.sdk" version="4.20.0.I20210611-1600"/>
            <repository location="http://download.eclipse.org/releases/2021-06/"/>
        </location>
        <location includeAllPlatforms="false" includeConfigurePhase="true" includeMode="planner" includeSource="true" type="InstallableUnit">
            <unit id="org.eclipse.babel.nls_eclipse_ja.feature.group" version="4.20.0.v20210630020001"/>
            <unit id="org.eclipse.babel.nls_eclipse_zh.feature.group" version="4.20.0.v20210630020001"/>
            <repository location="https://archive.eclipse.org/technology/babel/update-site/R0.19.0/2021-06/"/>
        </location>
    </locations>
    <targetJRE path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-11"/>
</target>
 */
open class PlatformConfigurationTargetBundleProvider : TargetBundleProvider {
    override val type: String = "Directory"


    override fun resolveDirectory(platformDefinitionTargetFile: File, processBundle: (File) -> Unit): Boolean {

        val jaxbContext = JAXBContext.newInstance(TargetDefinition::class.java)
        val targetUnMarshaller = jaxbContext.createUnmarshaller()

        val targetDefinition = targetUnMarshaller.unmarshal(platformDefinitionTargetFile) as TargetDefinition

        val locations = targetDefinition.locations ?: return true


        for (location in locations.location!!) {
            val type = location.type
            if (type == "InstallableUnit") {
                val units = location.unit
                if (units != null) {
                    for (unit in units) {
                        val id = unit.id
                        val version = unit.version
                        if (id != null && version != null) {
                            println("$id:$version")


                            // TODO get file
//                            processBundle()
                        }
                    }
                }
            }
        }

        return true
    }

    @XmlRootElement(name = "target")
    @XmlAccessorType(XmlAccessType.FIELD)
    class TargetDefinition {
        @XmlAttribute(name = "name") var name: String? = null

        @XmlAttribute(name = "sequenceNumber") var sequenceNumber: Int? = null

        @XmlElement(name = "locations") var locations: Locations? = null

        @XmlElement(name = "targetJRE") var targetJRE: TargetJRE? = null
    }

    @XmlRootElement(name = "locations")
    @XmlAccessorType(XmlAccessType.FIELD)
    class Locations {
        @XmlElement(name = "location") var location: List<Location>? = null
    }

    @XmlRootElement(name = "location")
    @XmlAccessorType(XmlAccessType.FIELD)
    class Location {
        @XmlAttribute(name = "includeAllPlatforms") var includeAllPlatforms: Boolean? = null

        @XmlAttribute(name = "includeConfigurePhase") var includeConfigurePhase: Boolean? = null

        @XmlAttribute(name = "includeMode") var includeMode: String? = null

        @XmlAttribute(name = "includeSource") var includeSource: Boolean? = null

        @XmlAttribute(name = "type") var type: String? = null

        @XmlElement(name = "unit") var unit: List<LocationUnit>? = null

        @XmlElement(name = "repository") var repository: List<LocationRepository>? = null
    }

    @XmlRootElement(name = "unit")
    @XmlAccessorType(XmlAccessType.FIELD)
    class LocationUnit {
        @XmlAttribute(name = "id") var id: String? = null

        @XmlAttribute(name = "version") var version: String? = null
    }

    @XmlRootElement(name = "repository")
    @XmlAccessorType(XmlAccessType.FIELD)
    class LocationRepository {
        @XmlAttribute(name = "location") var location: String? = null
    }

    @XmlRootElement(name = "targetJRE")
    @XmlAccessorType(XmlAccessType.FIELD)
    class TargetJRE {
        @XmlAttribute(name = "path") var path: String? = null
    }


}