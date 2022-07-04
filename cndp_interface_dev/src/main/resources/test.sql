 select numero_carte,--卡号
date_creation_carte --卡的创建时间
from
customeras1.personne
where id in
(select person_id::int8 from public.ods_sb_dkt_person where id in
(select distinct dkt_person_id from public.ods_sb_social_connection where social_id in
(select id from public.ods_sb_channel_person
where channel_person_id in ('o4zAjt3x_PeFJKpUK8teazQPZRK0', 'oph8M446FNAYmTyd8kpgxjrOu9EI', 'ob0m-4u4SOcZnJdjXlbmen9_XKtU' , 'ooETE5JStXt3Diqd_EL8sRdCVnoU',
'o4zAjtwHM_qj2XD_DL54chGnkTzY')))
)

dmt-pgs-prd.pg.rds.aliyuncs.com
service_account_talend_dataplatform


select
a.union_id,
d.numero_carte as card_nimber,
timezone('UTC',d.date_creation_carte) as registration_time,
d.num_tiers_createur as registration_store
from
(select channel_person_id as union_id, id from
public.ods_sb_channel_person
where channel_person_id in ('o4zAjt3x_PeFJKpUK8teazQPZRK0', 'oph8M446FNAYmTyd8kpgxjrOu9EI', 'ob0m-4u4SOcZnJdjXlbmen9_XKtU')) a
left join public.ods_sb_social_connection b
on a.id=b.social_id
left join public.ods_sb_dkt_person c
on b.dkt_person_id=c.id
left join customeras1.personne d
on c.person_id::int8=d.id
group by 1,2,3,4







