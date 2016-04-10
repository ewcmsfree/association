package com.ewcms.system.report.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.system.report.entity.Repository;
//import com.ewcms.content.resource.model.Resource;
//import com.ewcms.content.resource.service.ResourceService;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class RepositoryService extends BaseService<Repository, Long>{

//	@Autowired
//	private ResourceService resourceService;
	
//	public void publishRepository(List<Long> repositoryIds, Site site) {
//		for (Long repositoryId : repositoryIds) {
//			Repository repository = findRepositoryById(repositoryId);
//			String type = repository.getType();
//			byte[] bytes = repository.getEntity();
//			String outputFile = repository.getName() + "." + type;
//
//			Resource.Type resourceType = Resource.Type.ANNEX;
//			if (type.toLowerCase().equals("png")) {
//				resourceType = Resource.Type.IMAGE;
//			}
//
//			File file = null;
//			FileOutputStream fileStream = null;
//			BufferedOutputStream bufferStream = null;
//			try {
//				file = new File(outputFile);
//				fileStream = new FileOutputStream(file);
//				bufferStream = new BufferedOutputStream(fileStream);
//				bufferStream.write(bytes);
//				
//				resourceService.upload(site, file, outputFile, resourceType);
//				
//				repository.setPublishDate(new Date(Calendar.getInstance().getTime().getTime()));
//				repositoryDao.save(repository);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				if (fileStream != null) {
//					try {
//						fileStream.close();
//					} catch (IOException e) {
//					}
//					fileStream = null;
//				}
//				if (bufferStream != null) {
//					try {
//						bufferStream.close();
//					} catch (IOException e) {
//					}
//					bufferStream = null;
//				}
//				file = null;
//			}
//		}
//	}
}
