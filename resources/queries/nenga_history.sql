select
	N.RECEIVERADDRESSID,
	A.CAPTION || A.MEMO as RECEIVER,
	N.HONORIFIC,
	A.ZIPCODE,
	A.ADDRESSFROMZIPCODE || A.STREETNUMBERS || A.ROOMNAME as ADDRESS,
	N.MOURNING,
	N.SENTDATE,
	N.RECEIVEDDATE
from
	NENGAHISTORY as N
	left outer join ADDRESSES as A
	on N.RECEIVERADDRESSID = A.UUID
where
	N.YEAR = ? and
	N.SENDERADDRESSID = ?
order by
	A.ZIPCODE,
	A.ADDRESSFROMZIPCODE,
	A.STREETNUMBERS,
	A.ROOMNAME;
