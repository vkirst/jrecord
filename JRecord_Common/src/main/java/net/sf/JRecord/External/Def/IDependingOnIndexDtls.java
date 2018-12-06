package net.sf.JRecord.External.Def;

import java.util.List;

import net.sf.JRecord.common.AbstractRecordX;
import net.sf.JRecord.common.IFieldDetail;

public interface IDependingOnIndexDtls {

	DependingOn getDependingOn();

	int getIndex();

	void updateFieldInChildren(AbstractRecordX<? extends IFieldDetail> rec);

	List<DependingOn> getChildren();

}