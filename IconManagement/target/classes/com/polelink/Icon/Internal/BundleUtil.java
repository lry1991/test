/**
 *  Copyright (c) by Shanghai PoleLink Information Technology Co.,Ltd. All rights reserved.
 *
 *  This software is copyright protected and proprietary to Shanghai PoleLink
 *  Information Technology. Shanghai PoleLink Information Technology Co.,Ltd
 *  grants to you only those rights as set out in the license conditions.
 *  All other rights remain with Shanghai PoleLink Information Technology Co.,Ltd.
 *
 **/
package com.polelink.Icon.Internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.service.startlevel.StartLevel;
import org.osgi.util.tracker.ServiceTracker;

/**
 * 插件辅助类
 * 
 * 
 */
public class BundleUtil {



	/**
	 * 得到Bundle的类加载器
	 * 
	 * @param bundle
	 * @return
	 */
	public static ClassLoader getBundleClassLoader(Bundle bundle) {
		// 搜索Bundle中所有的class文件
		Enumeration<URL> classFileEntries = bundle.findEntries("/", "*.class",
				true);
		if (classFileEntries == null || !classFileEntries.hasMoreElements()) {
			throw new RuntimeException(String.format("Bundle[%s]中没有一个Java类！",
					bundle.getSymbolicName()));
		}
		// 得到其中的一个类文件的URL
		URL url = classFileEntries.nextElement();
		// 得到路径信息
		String bundleOneClassName = url.getPath();
		// 将"/"替换为"."，得到类名称
		bundleOneClassName = bundleOneClassName.replace("/", ".").substring(0,
				bundleOneClassName.lastIndexOf("."));
		// 如果类名以"."开头，则移除这个点
		while (bundleOneClassName.startsWith(".")) {
			bundleOneClassName = bundleOneClassName.substring(1);
		}
		Class<?> bundleOneClass = null;
		try {
			// 让Bundle加载这个类
			bundleOneClass = bundle.loadClass(bundleOneClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		// 得到Bundle的ClassLoader
		return bundleOneClass.getClassLoader();
	}

	public static void startBundle(BundleContext bundleContext, long bundleId)
			 {
		if (bundleId <= 0)
			return;
		Bundle bundle = bundleContext.getBundle(bundleId);
		// start bundle
		try {
			bundle.start();
		} catch (BundleException be) {
		
		}
	}

	public static void stopBundle(BundleContext bundleContext, long bundleId) {
		if (bundleId <= 0)
			return;
		Bundle bundle = bundleContext.getBundle(bundleId);
		// start bundle
		try {
			bundle.stop();
		} catch (BundleException be) {
		
		}
	}

	public static void uninstallBundle(BundleContext bundleContext,
			long bundleId) {
		if (bundleId <= 0)
			return;
		Bundle bundle = bundleContext.getBundle(bundleId);
		// start bundle
		try {
			bundle.uninstall();
		} catch (BundleException be) {

		}
	}

	public static void updateBundle(BundleContext bundleContext, long bundleId) {
		if (bundleId <= 0)
			return;
		Bundle bundle = bundleContext.getBundle(bundleId);
		// start bundle
		try {
			bundle.update();
		} catch (BundleException be) {

		}
	}

	public static Bundle installBundle(BundleContext bundleContext,
			String location, InputStream bundleStream, int startLevel,
			boolean startFlag, boolean refreshPackagesFlag)
			throws BundleException {

		Bundle bundle = bundleContext.installBundle(location, bundleStream);
		if (startLevel > 0) {
			StartLevel sl = (StartLevel) getService(bundleContext,
					StartLevel.class.getName());
			if (sl != null) {
				sl.setBundleStartLevel(bundle, startLevel);
			}
		}
		if (startFlag) {
			bundle.start();
		}
		return bundle;
	}



	public static String getBundleProperty(Bundle bundle, String name,
			String locale) {
		try {
			Dictionary headers = bundle.getHeaders(locale == null ? null
					: locale.toString());
			Object obj = headers.get(name);
			return obj == null ? null : obj.toString();
		} catch (Exception ex) {

			return null;
		}
	}

	public static Bundle getBundleById(BundleContext bundleContext,
			long bundleId) {
		return bundleContext.getBundle(bundleId);
	}

	/**
	 * Return a display name for the given <code>bundle</code>:
	 * <ol>
	 * <li>If the bundle has a non-empty <code>Bundle-Name</code> manifest
	 * header that value is returned.</li>
	 * <li>Otherwise the symbolic name is returned if set</li>
	 * <li>Otherwise the bundle's location is returned if defined</li>
	 * <li>Finally, as a last resort, the bundles id is returned</li>
	 * </ol>
	 * 
	 * @param bundle
	 *            the bundle which name to retrieve
	 * @param locale
	 *            the locale, in which the bundle name is requested
	 * @return the bundle name - see the description of the method for more
	 *         details.
	 */
	public static String getName(Bundle bundle, String locale) {
		String name = getBundleProperty(bundle, Constants.BUNDLE_NAME, locale);
		if (name == null || name.length() == 0) {
			name = bundle.getSymbolicName();
			if (name == null) {
				name = bundle.getLocation();
				if (name == null) {
					name = String.valueOf(bundle.getBundleId());
				}
			}
		}
		return name;
	}

	public static Integer getBundleStartLevel(Bundle bundle) {
		if (bundle.getState() != Bundle.UNINSTALLED) {
			StartLevel sl = (StartLevel) getService(bundle.getBundleContext(),
					StartLevel.class.getName());
			if (sl != null) {
				return new Integer(sl.getBundleStartLevel(bundle));
			}
		}
		// bundle has been uninstalled or StartLevel service is not available
		return null;
	}

	public static String getFileSymbolicName(File bundleFile) {
		JarFile jar = null;
		try {
			jar = new JarFile(bundleFile);
			Manifest m = jar.getManifest();
			if (m != null) {
				String sn = m.getMainAttributes().getValue(
						Constants.BUNDLE_SYMBOLICNAME);
				if (sn != null) {
					final int paramPos = sn.indexOf(';');
					if (paramPos != -1) {
						sn = sn.substring(0, paramPos);
					}
				}
				return sn;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (jar != null) {
				try {
					jar.close();
				} catch (IOException ioe) {
					// ignore
				}
			}
		}
		// fall back to "not found"
		return null;
	}

	private static Map<String, ServiceTracker> services = new HashMap<String, ServiceTracker>();

	public static Object getService(BundleContext bundleContext,
			String serviceName) {
		ServiceTracker serviceTracker = (ServiceTracker) services
				.get(serviceName);
		if (serviceTracker == null) {
			serviceTracker = new ServiceTracker(bundleContext, serviceName,
					null);
			serviceTracker.open();

			services.put(serviceName, serviceTracker);
		}

		return serviceTracker.getService();
	}

	private static PackageAdmin getPackageAdmin(BundleContext bundleContext) {
		return (PackageAdmin) getService(bundleContext,
				PackageAdmin.class.getName());
	}

	private static String getServiceID(ServiceReference ref) {
		String id = ref.getProperty(Constants.SERVICE_ID).toString();
		return id;
	}

	private static String getRefValue(ServiceReference ref, String name) {
		StringBuffer dest = new StringBuffer();
		Object value = ref.getProperty(name);
		if (value instanceof Object[]) {
			Object[] values = (Object[]) value;
			for (int j = 0; j < values.length; j++) {
				if (j > 0)
					dest.append(ConstantsCommon.CHAR_SERVICE_PROPERTY_SPLIT);
				dest.append(values[j]);
			}
		} else if (value != null) {
			dest.append(value);
		}
		return dest.toString();
	}

	public static List<ExportedPackage> getBundleExportedPackages(Bundle bundle) {
		if (bundle == null)
			return null;
		PackageAdmin packageAdmin = getPackageAdmin(bundle.getBundleContext());
		if (packageAdmin == null) {
			return null;
		}
		ExportedPackage[] exports = packageAdmin.getExportedPackages(bundle);
		if (exports == null || exports.length <= 0)
			return null;
		List<ExportedPackage> list = new ArrayList<ExportedPackage>();
		for (ExportedPackage export : exports) {
			list.add(export);
		}
		return list;
	}

	public static List<Bundle> getBundleImportBundles(Bundle bundle) {
		if (bundle == null)
			return null;
		PackageAdmin packageAdmin = getPackageAdmin(bundle.getBundleContext());
		if (packageAdmin == null) {
			return null;
		}
		ExportedPackage[] exports = packageAdmin.getExportedPackages(bundle);
		if (exports == null || exports.length <= 0)
			return null;

		// do alphabetical sort
		Arrays.sort(exports, new Comparator() {
			public int compare(ExportedPackage p1, ExportedPackage p2) {
				return p1.getName().compareTo(p2.getName());
			}

			public int compare(Object o1, Object o2) {
				return compare((ExportedPackage) o1, (ExportedPackage) o2);
			}
		});
		List<Bundle> list = new ArrayList<Bundle>();

		for (ExportedPackage export : exports) {
			Bundle[] ubList = export.getImportingBundles();
			if (ubList != null) {
				for (Bundle ub : ubList) {
					list.add(ub);
				}
			}
		}
		return list;
	}



	public static Bundle[] getBundles(BundleContext bundleContext) {
		try {
			return bundleContext.getBundles();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Bundle getBundleBySymbolicName(BundleContext bundleContext,
			String symbolicName) {
		try {
			Bundle[] bundles = bundleContext.getBundles();
			for (Bundle bundle : bundles) {
				if (bundle.getSymbolicName() != null
						&& bundle.getSymbolicName().equals(symbolicName))
					return bundle;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
