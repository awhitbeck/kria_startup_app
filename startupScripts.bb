#
# Recipe for all scripts that should be run at startup
#

SUMMARY = "Scripts that runs on boot"
SECTION = "PETALINUX/apps"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://mymatrix \
	file://rogue_app.py \
        file://startupScripts.service \
"

S = "${WORKDIR}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit update-rc.d systemd

RDEPENDS:${PN} += "bash"

INITSCRIPT_NAME = "mymatrix"
INITSCRIPT_PARAMS = "start 99 S ."

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "startupScripts.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
        if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
                install -d ${D}${sysconfdir}/init.d/
                install -m 0755 ${WORKDIR}/mymatrix ${D}${sysconfdir}/init.d/
        fi

        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/mymatrix ${D}${bindir}/
        install -d ${D}${bindir}    
        install -m 0755 ${WORKDIR}/rogue_app.py ${D}${bindir}/
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/startupScripts.service ${D}${systemd_system_unitdir}
}

FILES:${PN} += "${@bb.utils.contains('DISTRO_FEATURES','sysvinit','${sysconfdir}/*', '', d)}"
