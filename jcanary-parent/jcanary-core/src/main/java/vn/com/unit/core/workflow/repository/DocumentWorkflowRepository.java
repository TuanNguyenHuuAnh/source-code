package vn.com.unit.core.workflow.repository;

import vn.com.unit.core.entity.AbstractDocument;
import vn.com.unit.db.repository.DbRepository;

public interface DocumentWorkflowRepository extends DbRepository<AbstractDocument, Long> {
}