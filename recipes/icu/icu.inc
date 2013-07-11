SUMMARY = "International Component for Unicode libraries"
DESCRIPTION = "The International Component for Unicode (ICU) is a mature, portable set of C/C++ and Java libraries for Unicode support, software internationalization (I18N) and globalization (G11N), giving applications the same results on all platforms."
HOMEPAGE = "http://site.icu-project.org/"

LICENSE = "ICU"
DEPENDS = "icu-native"
DEPENDS_virtclass-native = ""

S = "${WORKDIR}/icu/source"
STAGING_ICU_SOURCE_DIR_NATIVE = "${COREBASE}/build/tmp-eglibc/work/x86_64-linux/icu-native-${PV}-${PR}/icu"

PARALLEL_MAKE = ""
FULL_OPTIMIZATION_arm = "-Os"

inherit autotools pkgconfig binconfig

# ICU needs the native build directory as an argument to its --with-cross-build option when
# cross-compiling. Taken the situation that different builds may share a common sstate-cache
# into consideration, the native build directory needs to be staged.
EXTRA_OECONF = "--with-cross-build=${STAGING_ICU_SOURCE_DIR_NATIVE}/source"
EXTRA_OECONF_virtclass-native = ""

# ICU puts custom m4 autoconf functions in aclocal.m4.
# However, this file is deleted in our build system. 
# To make it work, we copy aclocal.m4 to acinclude.m4.
# This is a bug of ICU. See bug reference:
# http://bugs.icu-project.org/trac/ticket/9790
do_configure_prepend() {
    [ -f acinclude.m4 ] || cp aclocal.m4 acinclude.m4
}

do_install_append() {
    if [ ${PN} == 'icu-native' ]; then
	mkdir -p ${D}/${STAGING_ICU_SOURCE_DIR_NATIVE}
	cp -r ${S} ${D}/${STAGING_ICU_SOURCE_DIR_NATIVE}
    fi
}

PACKAGES =+ "libicudata libicuuc libicui18n libicule libiculx libicutu libicuio"

FILES_${PN}-dev += "${libdir}/${BPN}/"

FILES_libicudata = "${libdir}/libicudata.so.*"
FILES_libicuuc = "${libdir}/libicuuc.so.*"
FILES_libicui18n = "${libdir}/libicui18n.so.*"
FILES_libicule = "${libdir}/libicule.so.*"
FILES_libiculx = "${libdir}/libiculx.so.*"
FILES_libicutu = "${libdir}/libicutu.so.*"
FILES_libicuio = "${libdir}/libicuio.so.*"

BBCLASSEXTEND = "native"