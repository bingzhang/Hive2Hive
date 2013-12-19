package org.hive2hive.core.process.upload.newfile;

import org.apache.log4j.Logger;
import org.hive2hive.core.log.H2HLoggerFactory;
import org.hive2hive.core.model.MetaFolder;
import org.hive2hive.core.process.common.put.PutMetaDocumentStep;

public class UpdateParentMetaStep extends PutMetaDocumentStep {

	private final static Logger logger = H2HLoggerFactory.getLogger(UpdateParentMetaStep.class);

	public UpdateParentMetaStep() {
		super(null, new UpdateUserProfileStep());
	}

	@Override
	public void start() {
		NewFileProcessContext context = (NewFileProcessContext) getProcess().getContext();
		logger.debug("Start updating the parent meta folder of file: " + context.getFile().getName());

		// add child to the parent meta data
		MetaFolder parentMeta = (MetaFolder) context.getMetaDocument();
		if (parentMeta == null) {
			getProcess().stop("Could not find the parent meta data");
		}

		parentMeta.addChildKeyPair(context.getNewMetaKeyPair());
		logger.debug("MetaFolder has new child. Total children = " + parentMeta.getChildKeys().size());

		super.metaDocument = parentMeta;
		super.start();
	}
}
