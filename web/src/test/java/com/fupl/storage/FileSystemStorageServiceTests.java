package com.fupl.storage;

import com.fupl.dao.repositories.FileRepository;
import com.fupl.entityes.FileEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FileSystemStorageServiceTests {

    private StorageProperties properties = new StorageProperties();
    private FileSystemStorageService service;

    FileRepository fileRepository;

    @Before
    public void init() {
        properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        fileRepository = mock(FileRepository.class);
        service = new FileSystemStorageService(properties, fileRepository);
        service.init();
    }

    @Test
    public void loadNonExistent() {
        assertThat(service.load("foo.csv")).doesNotExist();
    }

//    @Test
//    public void load() {
////        service.store(new MockMultipartFile("foo", "foo.csv", MediaType.TEXT_PLAIN_VALUE,
////                "Hello World".getBytes()));
////        assertThat(service.load("foo.csv")).exists();
//        Resource resource = mock(Resource.class);
//        when(resource.exists()).thenReturn(true);
//        when(resource.isReadable()).thenReturn(true);
//        when(save)
//        assertEquals(service.loadAsResource("foo.csv"), resource);
//    }

    @Test(expected = StorageException.class)
    public void saveNotPermitted() {
        service.store(new MockMultipartFile("foo", "../foo.csv",
                MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
    }

    @Test
    public void savePermitted() {
        service.store(new MockMultipartFile("foo", "bar/../foo.csv",
                MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
        verify(fileRepository).save(any(FileEntity.class));
    }

}
