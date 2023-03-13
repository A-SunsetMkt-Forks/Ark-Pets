; *** Inno Setup version 6.1.0+ Chinese Simplified messages ***
;
; To download user-contributed translations of this file, go to:
;   https://jrsoftware.org/files/istrans/
;
; Note: When translating this text, do not add periods (.) to the end of
; messages that didn't have them already, because on those messages Inno
; Setup adds the periods automatically (appending a period would result in
; two periods being displayed).
;
; Translated by Zhenghan Yang at 2022-2-21
;   https://github.com/kira-96/Inno-Setup-Chinese-Simplified-Translation
;
; Updated by Harry Huang at 2023-3-12
;


[LangOptions]

; The following three entries are very important. Be sure to read and 
; understand the '[LangOptions] section' topic in the help file.
LanguageName=��������
; If Language Name display incorrect, uncomment next line
; LanguageName=<7B80><4F53><4E2D><6587>
; About LanguageID, to reference link:
; https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-lcid/a9eac961-e77d-41a6-90a5-ce1a8b0cdb9c
LanguageID=$0804
LanguageCodePage=936
; If the language you are translating to requires special font faces or
; sizes, uncomment any of the following entries and change them accordingly.
;DialogFontName=
;DialogFontSize=8
;WelcomeFontName=Verdana
;WelcomeFontSize=12
;TitleFontName=Arial
;TitleFontSize=29
;CopyrightFontName=Arial
;CopyrightFontSize=8


[Messages]

; *** Ӧ�ó������
SetupAppTitle=��װ
SetupWindowTitle=��װ - %1
UninstallAppTitle=ж��
UninstallAppFullTitle=%1 ж��

; *** Misc. common
InformationTitle=��Ϣ
ConfirmTitle=��ȷ��
ErrorTitle=����

; *** SetupLdr messages
SetupLdrStartupMessage=���ڽ���װ %1������Ҫ������
LdrCannotCreateTemp=�޷�������ʱ�ļ�����װ����ֹ
LdrCannotExecTemp=�޷�ִ����ʱĿ¼�е��ļ�����װ����ֹ
HelpTextNote=

; *** ����������Ϣ
LastErrorMessage=%1.%n%n���� %2: %3
SetupFileMissing=��װĿ¼��ȱ���ļ� %1�������������������ԣ��������»�ȡ������°氲װ����
SetupFileCorrupt=��װ�ļ����𻵡������»�ȡ������°氲װ����
SetupFileCorruptOrWrongVer=��װ�ļ����𻵣������������װ����İ汾�����ݡ������������������ԣ��������»�ȡ������°氲װ����
InvalidParameter=��Ч�������в�����%n%n%1
SetupAlreadyRunning=��װ�����������С�
WindowsVersionNotSupported=�������֧�ֵ�ǰ����������е�Windows�汾��
WindowsServicePackRequired=���������Ҫ %1 ����� %2 ����ߡ�
NotOnThisPlatform=��������������� %1��
OnlyOnThisPlatform=���������������� %1��
OnlyOnTheseArchitectures=�������ֻ����Ϊ���д������ṹ��Ƶ�Windows�汾�н��а�װ��%n%n%1
WinVersionTooLowError=���������Ҫ %1 �汾 %2 ����ߡ�
WinVersionTooHighError=��������ܰ�װ�� %1 �汾 %2 ����ߡ�
AdminPrivilegesRequired=�ڰ�װ�������ʱ���������Թ���Ա������а�װ�򵼡�
PowerUserPrivilegesRequired=�ڰ�װ�������ʱ���������Թ���Ա��ݻ���Ȩ�޵��û�����ݵ�¼��
SetupAppRunningError=��װ������ %1 ��ǰ�������С�%n%n���ȹر�������صĳ���Ȼ������ȷ����������������ȡ�����˳���
UninstallAppRunningError=ж�س����� %1 ��ǰ�������С�%n%n���ȹر�������صĳ���Ȼ������ȷ����������������ȡ�����˳���

; *** ��������
PrivilegesRequiredOverrideTitle=ѡ��װģʽ
PrivilegesRequiredOverrideInstruction=ѡ�����İ�װģʽ
PrivilegesRequiredOverrideText1=%1 ����Ϊ���м�����û���װ(��Ҫ����ԱȨ��)�����Ϊ����װ��
PrivilegesRequiredOverrideText2=%1 ֻ��Ϊ����װ����Ϊ���м�����û���װ(��Ҫ����ԱȨ��)��
PrivilegesRequiredOverrideAllUsers=Ϊ�����û���װ(&A)
PrivilegesRequiredOverrideAllUsersRecommended=Ϊ�����û���װ(&A) (����ѡ��)
PrivilegesRequiredOverrideCurrentUser=ֻΪ�Ұ�װ(&M)
PrivilegesRequiredOverrideCurrentUserRecommended=ֻΪ�Ұ�װ(&M) (����ѡ��)

; *** ��������
ErrorCreatingDir=��װ���޷�����Ŀ¼��%1����
ErrorTooManyFilesInDir=�޷���Ŀ¼��%1���д����ļ�����Ϊ�������̫����ļ�

; *** ��װ���򹫹���Ϣ
ExitSetupTitle=�˳���װ����
ExitSetupMessage=��װ�򵼻�δ��ɰ�װ������������˳�����������ɰ�װ��%n%n�������Ժ������а�װ������ɰ�װ��%n%nҪ�����˳���װ������
AboutSetupMenuItem=���ڰ�װ����(&A)...
AboutSetupTitle=���ڰ�װ����
AboutSetupMessage=%1 �汾 %2%n%3%n%n%1 ��ҳ��%n%4
AboutSetupNote=
TranslatorNote=

; *** ��ť
ButtonBack=< ��һ��(&B)
ButtonNext=��һ��(&N) >
ButtonInstall=��װ(&I)
ButtonOK=ȷ��
ButtonCancel=ȡ��
ButtonYes=��(&Y)
ButtonYesToAll=ȫ��(&A)
ButtonNo=��(&N)
ButtonNoToAll=ȫ��(&O)
ButtonFinish=���(&F)
ButtonBrowse=���(&B)...
ButtonWizardBrowse=���(&R)...
ButtonNewFolder=�½��ļ���(&M)

; *** ��ѡ�����ԡ��Ի�����Ϣ
SelectLanguageTitle=ѡ��װ����
SelectLanguageLabel=��ѡ��װ����ʹ�õ����ԡ�

; *** ����������
ClickNext=�������һ����������������ȡ�����˳���װ����
BeveledLabel=
BrowseDialogTitle=����ļ���
BrowseDialogLabel=��������б���ѡ��һ���ļ��У�Ȼ������ȷ������
NewFolderName=�½��ļ���

; *** ����ӭ����ҳ
WelcomeLabel1=��ӭʹ�� [name] ��װ��
WelcomeLabel2=���ڽ���װ [name/ver] �����ĵ����С�%n%n�������ڼ�����װǰ�ر���������Ӧ�ó������練���������

; *** �����롱��ҳ
WizardPassword=����
PasswordLabel1=�����װ���������뱣����
PasswordLabel3=���������룬Ȼ��������һ�����������������ִ�Сд��
PasswordEditLabel=����(&P)��
IncorrectPassword=������������벻��ȷ�����������롣

; *** �����Э�顱��ҳ
WizardLicense=���Э��
LicenseLabel=���ڼ�����װǰ�Ķ�������Ҫ��Ϣ��
LicenseLabel3=����ϸ�Ķ��������Э�顣�ڼ�����װǰ������ͬ����ЩЭ�����
LicenseAccepted=��ͬ���Э��(&A)
LicenseNotAccepted=�Ҳ�ͬ���Э��(&D)

; *** ����Ϣ����ҳ
WizardInfoBefore=��Ϣ
InfoBeforeLabel=���ڼ�����װǰ�Ķ�������Ҫ��Ϣ��
InfoBeforeClickLabel=������������װ���������һ������
WizardInfoAfter=��Ϣ
InfoAfterLabel=���ڼ�����װǰ�Ķ�������Ҫ��Ϣ��
InfoAfterClickLabel=������������װ���������һ������

; *** ���û���Ϣ����ҳ
WizardUserInfo=�û���Ϣ
UserInfoDesc=������������Ϣ��
UserInfoName=�û���(&U)��
UserInfoOrg=��֯(&O)��
UserInfoSerial=���к�(&S)��
UserInfoNameRequired=�û����Ǳ����

; *** ��ѡ��װĿ¼����ҳ
WizardSelectDir=ѡ��װĿ¼
SelectDirDesc=���뽫 [name] ��װ�����
SelectDirLabel3=��װ�򵼽��� [name] ��װ��������ļ����С�
SelectDirBrowseLabel=�������һ�����������������ѡ�������ļ��У�������������
DiskSpaceGBLabel=������Ҫ�� [gb] GB �Ŀ��ô��̿ռ䡣
DiskSpaceMBLabel=������Ҫ�� [mb] MB �Ŀ��ô��̿ռ䡣
CannotInstallToNetworkDrive=�ó����޷�����װ��һ��������������
CannotInstallToUNCPath=�ó����޷�����װ��һ��UNC·����
InvalidPath=����������һ������������������·�������磺%n%nC:\APP%n%n �� UNC·����%n%n\\server\share
InvalidDrive=����ѡ���� ������ �� UNC���� �����ڻ��ܷ��ʡ���ѡ������λ�á�
DiskSpaceWarningTitle=���̿��ÿռ䲻��
DiskSpaceWarning=��װ����������Ҫ %1 KB �Ŀ��ÿռ䣬����ѡ������ֻ�� %2 KB �Ŀ��ÿռ䡣%n%n��һ��Ҫ������װ��
DirNameTooLong=�ļ������ƻ�·��̫����
InvalidDirName=�ļ���������Ч��
BadDirName32=�ļ������Ʋ��ܰ��������κ��ַ���%n%n%1
DirExistsTitle=�ļ����Ѵ���
DirExists=�ļ��У�%n%n%1%n%n�Ѵ��ڡ���һ��Ҫǿ�ư�װ������ļ�������
DirDoesntExistTitle=�ļ��в�����
DirDoesntExist=�ļ��У�%n%n%1%n%n�����ڡ�����Ҫ�������ļ�����

; *** ��ѡ���������ҳ
WizardSelectComponents=ѡ�����
SelectComponentsDesc=���밲װ��Щ����������
SelectComponentsLabel2=ѡ������Ҫ��װ�������ȥ�������밲װ�������Ȼ��������һ����������
FullInstallation=������װ
; if possible don't translate 'Compact' as 'Minimal' (I mean 'Minimal' in your language)
CompactInstallation=��లװ
CustomInstallation=�Զ��尲װ
NoUninstallWarningTitle=����Ѵ���
NoUninstallWarning=��װ�����⵽��������������ĵ����а�װ��%n%n%1%n%nȡ��ѡ����Щ��������ܳɹ���ж�����ǡ�%n%n��һ��Ҫ������
ComponentSize1=%1 KB
ComponentSize2=%1 MB
ComponentsDiskSpaceGBLabel=��ǰѡ������������Ҫ [gb] GB �Ŀ��ô��̿ռ䡣
ComponentsDiskSpaceMBLabel=��ǰѡ������������Ҫ [mb] MB �Ŀ��ô��̿ռ䡣

; *** ��ѡ�񸽼�������ҳ
WizardSelectTasks=ѡ�񸽼�����
SelectTasksDesc=����Ҫ��װ��ִ����Щ��������
SelectTasksLabel2=ѡ������Ҫ��װ���ڰ�װ [name] ʱִ�еĸ�������Ȼ��������һ����������

; *** ��ѡ��ʼ�˵��ļ��С���ҳ
WizardSelectProgramGroup=ѡ��ʼ�˵��ļ���
SelectStartMenuFolderDesc=��װ��Ӧ�������ﴴ������Ŀ�ʼ�˵���ݷ�ʽ��
SelectStartMenuFolderLabel3=��װ�򵼽�������Ŀ�ʼ�˵��ļ����д�������Ŀ�ݷ�ʽ��
SelectStartMenuFolderBrowseLabel=�������һ�����������������ѡ������λ�ã�������������
MustEnterGroupName=����������һ���ļ�������
GroupNameTooLong=�ļ�������·��̫����
InvalidGroupName=�ļ���������Ч�ġ�
BadGroupName=�ļ��������ܰ��������κ��ַ���%n%n%1
NoProgramGroupCheck2=��������ʼ�˵��ļ���(&D)

; *** ��׼����װ����ҳ
WizardReady=׼����װ
ReadyLabel1=��װ����׼���ã����ڿ��Կ�ʼ��װ [name] �����ĵ����С�
ReadyLabel2a=�������װ���Լ�����װ�˳����������Ҫ���²鿴���޸��κ����ã���������һ������
ReadyLabel2b=�������װ���Լ�����װ�˳���
ReadyMemoUserInfo=�û���Ϣ��
ReadyMemoDir=��װĿ¼��
ReadyMemoType=��װ���ͣ�
ReadyMemoComponents=��ѡ�����
ReadyMemoGroup=��ʼ�˵��ļ��У�
ReadyMemoTasks=��������

; *** TDownloadWizardPage wizard page and DownloadTemporaryFile
DownloadingLabel=�������ظ����ļ�...
ButtonStopDownload=ֹͣ����(&S)
StopDownload=��ȷ��Ҫֹͣ������
ErrorDownloadAborted=��������ֹ
ErrorDownloadFailed=����ʧ�ܣ�%1 %2
ErrorDownloadSizeFailed=��ȡ���ش�Сʧ�ܣ�%1 %2
ErrorFileHash1=У���ļ���ϣʧ�ܣ�%1
ErrorFileHash2=��Ч���ļ���ϣ��Ԥ�� %1��ʵ�� %2
ErrorProgress=��Ч�Ľ��ȣ�%1���ܹ�%2
ErrorFileSize=�ļ���С����Ԥ�� %1��ʵ�� %2

; *** ������׼����װ����ҳ
WizardPreparing=����׼����װ
PreparingDesc=��װ��������׼����װ [name] �����ĵ����С�
PreviousInstallNotCompleted=��ǰ����İ�װ/ж�ػ�δ��ɡ�����Ҫ�����������ĵ��Բ�����ɰ�װ��%n%n�������������Ժ������а�װ��� [name] �İ�װ��
CannotContinue=��װ�����ܼ�����������ȡ�����˳���
ApplicationsFound=����Ӧ�ó�������ʹ����Ҫ�ɰ�װ�򵼸��µ��ļ�������������װ���Զ��ر���ЩӦ�ó���
ApplicationsFound2=����Ӧ�ó�������ʹ����Ҫ�ɰ�װ�򵼸��µ��ļ�������������װ���Զ��ر���ЩӦ�ó��򡣰�װ��ɺ󣬰�װ�򵼽�����������ЩӦ�ó���
CloseApplications=�Զ��رո�Ӧ�ó���(&A)
DontCloseApplications=��Ҫ�رո�Ӧ�ó���(&D)
ErrorCloseApplications=��װ���޷��Զ��ر�����Ӧ�ó����ڼ���֮ǰ���������ֶ��ر���������ʹ����Ҫ�ɰ�װ�򵼸��µ��ļ���Ӧ�ó���
PrepareToInstallNeedsRestart=��װ��Ҫ�������������������������������������ٴ����а�װ��������� [name] �İ�װ��%n%n�Ƿ���������������

; *** �����ڰ�װ����ҳ
WizardInstalling=���ڰ�װ
InstallingLabel=�������ڰ�װ [name] �����ĵ����У��������ȴ�����

; *** ����װ��ɡ���ҳ
FinishedHeadingLabel=[name] ��װ���
FinishedLabelNoIcons=�������ĵ����гɹ���װ�� [name]��
FinishedLabel=�������ĵ����а�װ�� [name]����Ӧ�ó������ͨ�������ݷ�ʽ���С�
ClickFinish=�������ɡ����˳���װ�򵼡�
FinishedRestartLabel=Ҫ��� [name] �İ�װ�����Ǳ��������������ĵ��ԡ�����Ҫ��������������
FinishedRestartMessage=Ҫ��� [name] �İ�װ�����Ǳ��������������ĵ��ԡ�%n%n����Ҫ��������������
ShowReadmeCheck=�ǣ�������������ļ�
YesRadio=�ǣ�����������������(&Y)
NoRadio=���Ժ�������������(&N)
; used for example as 'Run MyProg.exe'
RunEntryExec=�������� %1
; used for example as 'View Readme.txt'
RunEntryShellExec=���� %1

; *** ����װ������Ҫ��һ�Ŵ��̡���ʾ
ChangeDiskTitle=��װ������Ҫ��һ�Ŵ���
SelectDiskLabel2=�������� %1 �������ȷ������%n%n�����������е��ļ������������ļ���֮����ļ������ҵ�����������ȷ��·���������������
PathLabel=·��(&P)��
FileNotInDir2=�ļ���%1�������ڡ�%2�����ҵ�������ȷ������̣���ѡ�������ļ��С�
SelectDirectoryLabel=��ָ����һ�Ŵ��̵�λ�á�

; *** ��װ״̬��Ϣ
SetupAborted=��װ����δ��ɰ�װ��%n%n������������Ⲣ�������а�װ����
AbortRetryIgnoreSelectAction=ѡ�����
AbortRetryIgnoreRetry=����(&T)
AbortRetryIgnoreIgnore=���Դ��󲢼���(&I)
AbortRetryIgnoreCancel=�ر���

; *** ��װ״̬��Ϣ
StatusClosingApplications=���ڹرճ���...
StatusCreateDirs=���ڴ���Ŀ¼...
StatusExtractFiles=���ڽ�ѹ���ļ�...
StatusCreateIcons=���ڴ�����ݷ�ʽ...
StatusCreateIniEntries=���ڴ��� INI ��Ŀ...
StatusCreateRegistryEntries=���ڴ���ע�����Ŀ...
StatusRegisterFiles=����ע���ļ�...
StatusSavingUninstall=���ڱ���ж����Ϣ...
StatusRunProgram=���Ͼͺ�...
StatusRestartingApplications=��������Ӧ�ó���...
StatusRollback=���ڳ�������...

; *** ��������
ErrorInternal2=�ڲ�����%1
ErrorFunctionFailedNoCode=%1 ʧ��
ErrorFunctionFailed=%1 ʧ�ܣ�������� %2
ErrorFunctionFailedWithMessage=%1 ʧ�ܣ�������� %2.%n%3
ErrorExecutingProgram=�޷�ִ�и��ļ���%n%1

; *** ע������
ErrorRegOpenKey=��ע�����ʱ����%n%1\%2
ErrorRegCreateKey=����ע�����ʱ����%n%1\%2
ErrorRegWriteKey=д��ע�����ʱ����%n%1\%2

; *** INI ����
ErrorIniEntry=���ļ���%1���д���INI��Ŀʱ����

; *** �ļ����ƴ���
FileAbortRetryIgnoreSkipNotRecommended=��������ļ�(&S) (���Ƽ�)
FileAbortRetryIgnoreIgnoreNotRecommended=���Դ��󲢼���(&I) (���Ƽ�)
SourceIsCorrupted=Դ�ļ�����
SourceDoesntExist=Դ�ļ���%1��������
ExistingFileReadOnly2=�޷��滻�����ļ�����Ϊ����ֻ���ġ�
ExistingFileReadOnlyRetry=�Ƴ�ֻ�����Բ�����(&R)
ExistingFileReadOnlyKeepExisting=���������ļ�(&K)
ErrorReadingExistingDest=���Զ�ȡ�����ļ�ʱ����
FileExistsSelectAction=ѡ�����
FileExists2=�ļ��Ѿ����ڡ�
FileExistsOverwriteExisting=�����Ѿ����ڵ��ļ�(&O)
FileExistsKeepExisting=�������е��ļ�(&K)
FileExistsOverwriteOrKeepAll=Ϊ���еĳ�ͻ�ļ�ִ�д˲���(&D)
ExistingFileNewerSelectAction=ѡ�����
ExistingFileNewer2=���е��ļ��Ȱ�װ����Ҫ��װ���ļ����¡�
ExistingFileNewerOverwriteExisting=�����Ѿ����ڵ��ļ�(&O)
ExistingFileNewerKeepExisting=�������е��ļ�(&K) (�Ƽ�)
ExistingFileNewerOverwriteOrKeepAll=Ϊ���еĳ�ͻ�ļ�ִ�д˲���(&D)
ErrorChangingAttr=�����޸��������е��ļ�������ʱ����
ErrorCreatingTemp=������Ŀ��Ŀ¼�����ļ�ʱ����
ErrorReadingSource=���Զ�ȡ����Դ�ļ�ʱ����
ErrorCopying=���Ը��������ļ�ʱ����
ErrorReplacingExistingFile=�����滻���е��ļ�ʱ����
ErrorRestartReplace=���������滻ʧ�ܣ�
ErrorRenamingTemp=��������������Ŀ��Ŀ¼�е�һ���ļ�ʱ����
ErrorRegisterServer=�޷�ע�� DLL/OCX��%1
ErrorRegSvr32Failed=RegSvr32 ʧ�ܣ��˳����� %1
ErrorRegisterTypeLib=�޷�ע�����Ϳ⣺%1

; *** ж����ʾ���ֱ��
; used for example as 'My Program (32-bit)'
UninstallDisplayNameMark=%1 (%2)
; used for example as 'My Program (32-bit, All users)'
UninstallDisplayNameMarks=%1 (%2, %3)
UninstallDisplayNameMark32Bit=32λ
UninstallDisplayNameMark64Bit=64λ
UninstallDisplayNameMarkAllUsers=�����û�
UninstallDisplayNameMarkCurrentUser=��ǰ�û�

; *** ��װ�����
ErrorOpeningReadme=���Դ������ļ�ʱ����
ErrorRestartingComputer=��װ�����������������ԣ����ֶ�������

; *** ж����Ϣ
UninstallNotFound=�ļ���%1�������Ѷ�ʧ���޷�����ж�ء�
UninstallOpenError=�ļ���%1���޷��򿪡��޷�����ж�ء�
UninstallUnsupportedVer=�˰汾��ж�س����޷�ʶ��ж����־�ļ���%1���ĸ�ʽ���޷�����ж�ء�
UninstallUnknownEntry=��ж����־������һ��δ֪����Ŀ (%1)
ConfirmUninstall=��ȷ����Ҫ��ȫж�� %1 �������������
UninstallOnlyOnWin64=�ó���ֻ���� 64λWindows �н���ж�ء�
OnlyAdminCanUninstall=�ó�����Ҫ�й���ԱȨ�޵��û�����ж�ء�
UninstallStatusLabel=���ڴ����ĵ�����ж�� %1�����Եȡ�
UninstalledAll=%1 ��˳���ش����ĵ�����ж�ء�
UninstalledMost=%1 ж����ɡ�%n%n��һЩ�����޷���ɾ�����������Ժ��ֶ�ɾ�����ǡ�
UninstalledAndNeedsRestart=Ҫ��� %1 ��ж�أ����ĵ��Ա�������������%n%n����������������������
UninstallDataCorrupted=�ļ���%1�����𻵣��޷�ɾ��

; *** ж��״̬��Ϣ
ConfirmDeleteSharedFileTitle=ɾ�������ļ���
ConfirmDeleteSharedFile2=ϵͳ�а��������й����ļ��Ѿ����ٱ���������ʹ�á�����Ҫж�س���ɾ����Щ�����ļ���%n%n�����Щ�ļ���ɾ���������г�������ʹ����Щ�ļ�����Щ������ܲ����������С��������ȷ������ѡ�񡰷񡱣��԰���Щ�ļ�������ϵͳ�������������⡣
SharedFileNameLabel=�ļ�����
SharedFileLocationLabel=λ�ã�
WizardUninstalling=ж��״̬
StatusUninstalling=����ж�� %1 ...

; *** �ػ�����ԭ��
ShutdownBlockReasonInstallingApp=���ڰ�װ %1 �С���
ShutdownBlockReasonUninstallingApp=����ж�� %1 �С���


; The custom messages below aren't used by Setup itself, but if you make
; use of them in your scripts, you'll want to translate them.

[CustomMessages]

NameAndVersion=%1 �汾 %2
AdditionalIcons=���ӿ�ݷ�ʽ��
CreateDesktopIcon=���������ݷ�ʽ(&D)
CreateQuickLaunchIcon=����������������ݷ�ʽ(&Q)
ProgramOnTheWeb=%1 ��վ
UninstallProgram=ж�� %1
LaunchProgram=�������� %1
AssocFileExtension=���ļ���չ�� %2 �� %1 ��������(&A)
AssocingFileExtension=���ڽ��ļ���չ�� %2 �� %1 ��������...
AutoStartProgramGroupDescription=�����飺
AutoStartProgram=�Զ����� %1
AddonHostProgramNotFound=%1�޷��ҵ�����ѡ����ļ��С�%n%n����Ҫ������
