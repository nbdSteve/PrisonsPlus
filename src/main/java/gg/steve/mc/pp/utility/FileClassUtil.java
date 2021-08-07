package gg.steve.mc.pp.utility;

import gg.steve.mc.pp.file.FileManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

@UtilityClass
public class FileClassUtil {

    public static List<Class<?>> getClasses(JavaPlugin plugin, String folder, Class<?> type) {
        return getClasses(plugin, folder, null, type);
    }

    public static List<Class<?>> getClasses(JavaPlugin plugin, String folder, String fileName, Class<?> type) {
        List<Class<?>> list = new ArrayList<>();

        try {
            File f = new File(plugin.getDataFolder(), folder);
            if (!f.exists()) {
                return list;
            }
            FilenameFilter fileNameFilter = (dir, name) -> {
                if (fileName != null) {
                    return name.endsWith(".jar") && name.replace(".jar", "")
                            .equalsIgnoreCase(fileName.replace(".jar", ""));
                }
                return name.endsWith(".jar");
            };
            File[] jars = f.listFiles(fileNameFilter);
            if (jars == null) {
                return list;
            }
            for (File file : jars) {
                list = gather(file.toURI().toURL(), list, type);
            }
            return list;
        } catch (Throwable t) {
        }
        return null;
    }

    public static void loadAddonFiles(JavaPlugin plugin, String addonName, String folder, Class<?> type) {
        try {
            File f = new File(plugin.getDataFolder(), folder);
            if (!f.exists()) return;
            FilenameFilter fileNameFilter = (dir, name) -> name.endsWith(".jar") && name.replace("Addon.jar", "").equalsIgnoreCase(addonName);
            File[] jars = f.listFiles(fileNameFilter);
            if (jars == null) return;
            for (File file : jars) {
                String dataFolderName = file.getName().split("Addon.jar")[0].toLowerCase(Locale.ROOT);
                gatherFiles(plugin, file.toURI().toURL(), type, dataFolderName);
            }
        } catch (Throwable t) {
        }
    }

    private static void gatherFiles(JavaPlugin plugin, URL jar, Class<?> clazz, String dataFolderName) {
        try (URLClassLoader cl = new URLClassLoader(new URL[]{jar}, clazz.getClassLoader());
             JarInputStream jis = new JarInputStream(jar.openStream())) {

            while (true) {
                JarEntry j = jis.getNextJarEntry();
                if (j == null) {
                    break;
                }

                String name = j.getName();
                if (name == null || name.isEmpty()) {
                    continue;
                }
                if (name.endsWith(".yml")) {
                    File dataFolder = new File(plugin.getDataFolder() + File.separator + dataFolderName);
                    if (!dataFolder.exists()) dataFolder.mkdirs();

                    InputStream is = cl.getResourceAsStream(name);
                    if (is != null) {
                        try {
                            File file = null;
                            if (name.contains("/")) {
                                String[] folders = name.split("/");
                                StringBuilder path = new StringBuilder();
                                path.append(folders[0]);
                                for (int i = 0; i < folders.length; i++) {
                                    if (folders[i].endsWith(".yml")) {
                                        file = new File(plugin.getDataFolder() + File.separator + dataFolderName + File.separator + path, folders[i]);
//                                        if (!file.exists()) file.createNewFile();
                                    } else {
//                                        LogUtil.warning(path.toString());
                                        File dir = new File(plugin.getDataFolder() + File.separator + dataFolderName + File.separator + path);
                                        if (!dir.exists()) dir.mkdirs();
//                                        if (i + 1 <= folders.length) path.append(File.separator + folders[i + 1]);
                                    }
                                }
                            } else {
                                file = new File(plugin.getDataFolder() + File.separator + dataFolderName, name);
//                                if (!file.exists()) file.createNewFile();
                            }
                            if (file != null && !file.exists()) {
                                file.createNewFile();
                                FileOutputStream output = new FileOutputStream(file);
                                int length;
                                byte[] bytes = new byte[1024];

                                while ((length = is.read(bytes)) != -1) {
                                    output.write(bytes, 0, length);
                                }
                            }
                            // Register the file and the yml config with the file manager
                            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                            String key = configuration.getString("file-type");
                            FileManager.getInstance().registerFile(key, name.replaceAll("/", "-").split(".yml")[0], file);
                        } catch (IOException e) {
                        }
                    }
                }
            }
        } catch (Throwable t) {
        }
    }

    private static List<Class<?>> gather(URL jar, List<Class<?>> list, Class<?> clazz) {
        if (list == null) {
            list = new ArrayList<>();
        }
        try (URLClassLoader cl = new URLClassLoader(new URL[]{jar}, clazz.getClassLoader());
             JarInputStream jis = new JarInputStream(jar.openStream())) {

            while (true) {
                JarEntry j = jis.getNextJarEntry();
                if (j == null) {
                    break;
                }

                String name = j.getName();
                if (name == null || name.isEmpty()) {
                    continue;
                }

                if (name.endsWith(".class")) {
                    name = name.replace("/", ".");
                    String cname = name.substring(0, name.lastIndexOf(".class"));

                    Class<?> c = cl.loadClass(cname);
                    if (clazz.isAssignableFrom(c)) {
                        list.add(c);
                    }
                }
            }
        } catch (Throwable t) {
        }
        return list;
    }
}