package dev.harshit.filestorage;

import dev.harshit.filestorage.models.File;
import dev.harshit.filestorage.models.FileShare;
import dev.harshit.filestorage.models.FileVersion;
import dev.harshit.filestorage.repositories.FileRepository;
import dev.harshit.filestorage.repositories.FileShareRepository;
import dev.harshit.filestorage.repositories.FileVersionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class FileStorageApplicationTests {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private FileVersionRepository fileVersionRepository;

	@Autowired
	private FileShareRepository fileShareRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testingQuery() {
		File file = new File();
		file.setName("def.txt");
		file.setPath("From A to B");
		file.setUploadTime(LocalDateTime.now());
		file = fileRepository.save(file);

		Optional<File> foundFile = fileRepository.findById(file.getId());
		if (foundFile.isPresent()) {
			System.out.println("File found: " + foundFile.get().getName());
		} else {
			System.out.println("File not found");
		}
	}

	@Test
	public void testingQuery2() {
		File file = new File();
		file.setName("def.txt");
		file.setUploadTime(LocalDateTime.now());
		fileRepository.save(file);

		FileVersion fileVersion = new FileVersion();
		fileVersion.setFile(file);
		fileVersion.setUploadTime(LocalDateTime.now());
		fileVersion.setVersionName("v2");
		fileVersionRepository.save(fileVersion);

		FileVersion fileVersion2 = fileVersionRepository.findByFileIdAndVersionName(file.getId(), "v2");
		if (fileVersion2 != null) {
			System.out.println("File version found: " + fileVersion2.getVersionName());
			System.out.println("File uploaded at: " + fileVersion2.getUploadTime());
		} else  {
			System.out.println("File version not found");
		}
	}

	@Test
	public void testingQuery3() {
		File file = new File();
		file.setName("def.txt");
		file.setUploadTime(LocalDateTime.now());
		file = fileRepository.save(file);

		FileShare fileShare = new FileShare();
		fileShare.setFile(file);
		fileShare.setSharedWith("pqr");
		fileShareRepository.save(fileShare);

		List<FileShare> fileShares = fileShareRepository.findBySharedWith("pqr");
		if (!fileShares.isEmpty()) {
			for (FileShare fileShare1 : fileShares) {
				System.out.println("File shared with: " + fileShare1.getSharedWith()
						+ ", File name: " + fileShare1.getFile().getName());
			}
		} else {
			System.out.println("File share not found");
		}
	}

	@Test
	public void testingQuery4() {
		FileVersion fileVersion = new FileVersion();
		fileVersion = fileVersionRepository.findByFileIdAndVersionName(1L, "v1");
		System.out.println(fileVersion.getVersionName());
		System.out.println(fileVersion.getUploadTime());
	}
}
