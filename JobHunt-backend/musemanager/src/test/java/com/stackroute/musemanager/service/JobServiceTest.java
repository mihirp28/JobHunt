package com.stackroute.musemanager.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.musemanager.MusemanagerApplication;
import com.stackroute.musemanager.domain.Job;
import com.stackroute.musemanager.exception.JobAlreadyExistsException;
import com.stackroute.musemanager.exception.JobNotFoundException;
import com.stackroute.musemanager.repository.JobRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class JobServiceTest {


	@InjectMocks
	private JobServiceImpl jobService;
	
	@Mock
	private JobRepository jobRepo;
	
	private Job job;
	private List<Job> jobList = new ArrayList<>();
	
	transient Optional<Job> options;
	
	@Before
	public void setUpMocks() {
		MockitoAnnotations.openMocks(this);
		job = new Job(0, "1111", "full stack developer", "26/3/2019", "Chennai", "CTS", "pending", "admin");
		jobList.add(new Job(0, "1111", "full stack developer", "26/3/2019", "Chennai", "CTS", "pending", "admin"));
		jobList.add(new Job(0, "2222", "java developer", "26/3/2019", "Delhi", "RS Components", "pending", "admin"));
		jobList.add(new Job(0, "3333", "web developer", "26/3/2019", "Gurgaon", "ANR software solutions pvt ltd", "pending", "admin"));
		options = Optional.of(job);
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull("Failure in injecting mocks, use proper annotations", job);
	}
	
	@Test
	public void testSaveNewJobSuccess() throws JobAlreadyExistsException {
		when(jobRepo.save(job)).thenReturn(job);
		boolean flag= jobService.saveJob(job);
		assertTrue("Creation of new job failed",flag);
		verify(jobRepo, times(1)).save(job);
		
	}
	
	@Test(expected = JobAlreadyExistsException.class)
	public void testSaveNewJobFailure() throws JobAlreadyExistsException {
		when(jobRepo.findByJobIdAndUserId("1111", "admin")).thenReturn(options);
		when(jobRepo.save(job)).thenReturn(job);
		final boolean flag = jobService.saveJob(job);
		assertFalse("Saving job failed",flag);
		verify(jobRepo, times(1)).findByJobIdAndUserId("1111", "admin");

	}
	@Test
	public void testGetJobById() throws JobNotFoundException {
		when(jobRepo.findByJobIdAndUserId("1111", "admin")).thenReturn(options);
		final Job fetchedJob = jobService.fetchJobById("1111", "admin");
		assertEquals("Fetching Job by Id failed",job,fetchedJob);
		verify(jobRepo, times(1)).findByJobIdAndUserId("1111", "admin");
	}
	
	@Test
	public void testUpdateJob() throws JobNotFoundException {
		jobRepo.save(job);
		when(jobRepo.findByJobIdAndUserId("1111", "admin")).thenReturn(options);
		when(jobRepo.save(job)).thenReturn(job);
		job.setStatus("Applied");
		Job updatedJob = jobService.updateJobStatus(job);
		assertEquals("Applied", updatedJob.getStatus());
		verify(jobRepo,times(2)).save(job);
		verify(jobRepo,times(1)).findByJobIdAndUserId("1111", "admin");
	}
	
	@Test
	public void testFetchAllJobs() {
		List<Job> fetchedJobList = new ArrayList<>();
		when(jobRepo.findByUserId("admin")).thenReturn(jobList);
		fetchedJobList = jobService.fetchAlljobs("admin");
		assertThat(fetchedJobList).hasSize(3);
		verify(jobRepo, times(1)).findByUserId("admin");
	}
	
	@Test
	public void testDeleteJobById() throws JobNotFoundException {
		when(jobRepo.findByJobIdAndUserId("1111", "admin")).thenReturn(options);
		boolean flag = jobService.deleteJobById("1111", "admin");
		assertTrue("deleting job failed", flag);
		verify(jobRepo,times(1)).findByJobIdAndUserId("1111", "admin");
	}
	
	

}

