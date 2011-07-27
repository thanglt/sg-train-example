package com.m3958.firstgwt.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.model.Asset;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoAssetRepository extends AssetRepositoryImpl implements AssetRepository{
	
	/*
	 * 
	 * GridFS is a storage specification for large objects in MongoDB. 
	 * It works by splitting large object into small chunks, usually 256k in size. 
	 * Each chunk is stored as a separate document in a chunks collection. 
	 * Metadata about the file, including the filename, content type, and any optional information needed by the developer, 
	 * is stored as a document in a files collection.

		So for any given file stored using GridFS, 
		there will exist one document in files collection and one or more documents in the chunks collection.
	 */
	
	@Inject
	private MongoDbService mdbs;

	@Override
	public InputStream get(Asset asset) {
		return null;
	}

	@Override
	public Asset save(String siteId,UploadFor ufor,String fname, String contentType, InputStream stream)
			throws FileNotFoundException, IOException {
		GridFSInputFile gf = mdbs.getGfs().createFile(stream);
		gf.setFilename(fname);
		gf.setContentType(contentType);
		gf.save();
		Asset a = createAsset();
		a.setFileSize(gf.getLength());
		a.setFileId(gf.getId().toString());
		return saveAsset(a);
	}

}
