namespace java org.kelab.swustoj.rpc

const string SWUST_OJ = "swustoj";
const string PATEST = "patest";

struct JudgeCase{
	1:required i32 caseId;
	2:required string input;
	3:required string output;
}

struct JudgeCaseResult {
	1:required i32 problemId;
	2:required list<JudgeCase> cases;
}

struct JudgeCaseQuery{
	1:required string appName;
	2:required i32 problemId;
}

service JudgeCaseService {
    JudgeCaseResult query(1:JudgeCaseQuery query);
    bool save(1:JudgeCaseQuery query,2:list<JudgeCase> cases);
    bool remove(1:JudgeCaseQuery query,2:list<i32> caseIds);
}