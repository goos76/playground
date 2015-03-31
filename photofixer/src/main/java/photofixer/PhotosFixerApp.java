package photofixer;

import java.io.File;

public class PhotosFixerApp {

	public static void main(String[] args) {

		File photoDir = new File("C:/GoogleDrive/GoDocs Photos");

		PhotoFixer photoFixer = new PhotoFixer(photoDir);
		photoFixer.createDuplicateList();
		photoFixer.deleteDuplicates(true);

	}

}
