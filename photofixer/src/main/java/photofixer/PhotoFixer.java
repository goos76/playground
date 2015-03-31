package photofixer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

class PhotoFixer {

	private final File photoDir;
	private final HashMap<String, List<File>> photoMapByPrefix = new HashMap<>();
	private int filesDeleted = 0;

	PhotoFixer(File photoDir) {
		this.photoDir = photoDir;
	}

	void createDuplicateList() {
		if (photoDir.isDirectory()) {
			File[] photos = photoDir.listFiles();
			for (File file : photos) {
				if (file.isFile()) {
					String prefix = StringUtils.substringBefore(file.getName(), "(")
							+ StringUtils.substringAfter(file.getName(), ")");
					Long size = file.length();
					prefix = prefix + "_" + size;
					prefix = StringUtils.remove(prefix, " ");
					// System.out.println("prefix voor " + file.getName() +
					// " is " + prefix);
					if (!photoMapByPrefix.containsKey(prefix)) {
						photoMapByPrefix.put(prefix, new ArrayList<File>());
					}
					photoMapByPrefix.get(prefix).add(file);

				}
			}
		}
		System.out.println("created duplicate list for photo dir " + photoDir.getAbsolutePath());
	}

	void deleteDuplicates(boolean delete) {

		for (String prefix : photoMapByPrefix.keySet()) {
			List<File> duplicatePhotos = photoMapByPrefix.get(prefix);
			if (duplicatePhotos.size() > 1) {
				// System.out.println("duplicates for " + prefix);
				List<File> photosToRemove = new ArrayList<>();
				for (int i = 0; i < duplicatePhotos.size() - 1; i++) {
					File file = duplicatePhotos.get(i);
					// System.out.println("duplicate photo " + file.getName() +
					// " with size " + file.length());
					photosToRemove.add(file);

				}
				System.out.println("duplicate fotos " + duplicatePhotos.size() + " and photos to remove is "
						+ photosToRemove.size());
				if (duplicatePhotos.size() <= photosToRemove.size()) {
					throw new RuntimeException("duplicate fotos " + duplicatePhotos.size()
							+ " and photos to remove is " + photosToRemove.size());
				}
				for (File file : photosToRemove) {
					System.out
							.println("duplicate photo " + file.getName() + " with size " + file.length() + " deleted");

					if (delete) {
						file.delete();
					}
					filesDeleted++;
				}

			}

		}
		System.out.println("deleted " + filesDeleted + " fotos ");

	}

}
