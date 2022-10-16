package com.zerobase.zerolms.member.service.Impl;

import com.zerobase.zerolms.admin.dto.MemberDto;
import com.zerobase.zerolms.admin.mapper.MemberMapper;
import com.zerobase.zerolms.admin.model.MemberParam;
import com.zerobase.zerolms.components.MailComponents;
import com.zerobase.zerolms.course.model.ServiceResult;
import com.zerobase.zerolms.member.entity.Member;
import com.zerobase.zerolms.member.entity.MemberCode;
import com.zerobase.zerolms.member.exception.MemberNotEmailAuthException;
import com.zerobase.zerolms.member.exception.MemberStopUserException;
import com.zerobase.zerolms.member.model.MemberInput;
import com.zerobase.zerolms.member.model.ResetPasswordInput;
import com.zerobase.zerolms.member.repository.MemberRepository;
import com.zerobase.zerolms.member.service.MemberService;
import com.zerobase.zerolms.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MailComponents mailComponents;
    private final MemberRepository memberRepository;

    private final MemberMapper mapper;
    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()){
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(),BCrypt.gensalt());


        String uuid= UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .userStatus(Member.MEMBER_STATUS_REQ)
                .emailAuthKey(uuid).build();


        memberRepository.save(member);

        /*과제? 관리자페이지 메일수정*/
        String email =parameter.getUserId();
        String subject = "fastlms 사이트 가입을 축하드립니다.";
        String text="<p> fastlms 사이트 가입을 축하드립니다. </p><p> 아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                    +"<div><a href= 'http://localhost:8080/member/email-auth?id="+ uuid +"'>가입 완료</a> </div>";

        mailComponents.sendMail(email,subject,text);

        return true;
    }


    @Override
    public boolean emailAuth(String uuid) {



       Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }

        Member member = optionalMember.get();

        if(member.isEmailAuthYn()){
            return false;
        }
        member.setUserStatus(MemberCode.MEMBER_STATUS_ING);
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }



    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter){

        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(),parameter.getUserName());

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email =parameter.getUserId();
        String subject = "[fastlms] 비밀번호 초기화 메일입니다.";
        String text="<p>[fastlms] 비밀번호 초기화 메일입니다. </p><p> 아래 링크를 클릭하셔서 비밀번호초기화를 완료하세요.</p>"
                +"<div><a target='_blank' href= 'http://localhost:8080/member/reset/password?id="+ uuid +"'>비밀번호초기화 링크</a></div>";

        mailComponents.sendMail(email,subject,text);
        return true;
    }


    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member =optionalMember.get();
        //초기화 날짜가 유효한지 체크
        if(member.getResetPasswordLimitDt() ==null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        String encPassword=BCrypt.hashpw(password,BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);
        return true;

    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if(!optionalMember.isPresent()){
          return false;
        }

        Member member =optionalMember.get();
        //초기화 날짜가 유효한지 체크
        if(member.getResetPasswordLimitDt() ==null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;
    }

    @Override
    public List<MemberDto> list(MemberParam param) {

         long totalCount = mapper.selectListCount(param);
         List<MemberDto> list =mapper.selectList(param);
        if(!CollectionUtils.isEmpty(list)){
            int i=0;
            for (MemberDto x:list) {
                x.setTotalCount(totalCount);
                x.setSeq( totalCount-param.getPageStart()-i);
               i++;
            }
        }

         return list;


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member =optionalMember.get();

        if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())){
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인해주세요. ");
        }
        if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())){
            throw new MemberStopUserException("정지된 회원입니다. ");
        }

        if(Member.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())){
            throw new MemberStopUserException("탈퇴된 회원입니다. ");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(member.isAdminYn()){
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(),member.getPassword(),grantedAuthorityList);

    }

    @Override
    public MemberDto detail(String userId) {

        Optional<Member> optionMember= memberRepository.findById(userId);
        if(!optionMember.isPresent()){
            return null;
        }
        Member member = optionMember.get();

        return MemberDto.of(member);
    }

    @Override
    public boolean updateStatus(String userStatus, String userId) {

        Optional<Member> optionalMember = memberRepository.findById(userId);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        member.setUserStatus(userStatus);
        memberRepository.save(member);


        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {

        Optional<Member> optionalMember = memberRepository.findById(userId);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        String encPassword=BCrypt.hashpw(password,BCrypt.gensalt());

        member.setPassword(encPassword);
        memberRepository.save(member);

        return true;

    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput param) {

        String userId= param.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
           return new ServiceResult(false,"회원정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();


        if(!PasswordUtils.equals(param.getPassword(),member.getPassword())){
            return  new ServiceResult(false,"비밀번호가 일치하지 않습니다.");
        }

        String encPassword = PasswordUtils.encPassword(param.getNewPassword());
        member.setPassword(encPassword);
        memberRepository.save(member);


        return new ServiceResult(true);
    }

    @Override
    public ServiceResult updateMember(MemberInput param) {

        String userId= param.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false,"회원정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        member.setPhone(param.getPhone());
        member.setZipcode(param.getZipcode());
        member.setAddr(param.getAddr());
        member.setAddrDetail(param.getAddrDetail());
        member.setUdtDt(LocalDateTime.now());
        memberRepository.save(member);

        return new ServiceResult(true);

    }

    @Override
    public ServiceResult withdraw(String userId,String password) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false,"회원정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if (!PasswordUtils.equals(password,member.getPassword())) {
            return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");

        }

        member.setUserName("삭제회원");
        member.setPhone("");
        member.setPassword("");
        member.setRegDt(null);
        member.setUdtDt(null);
        member.setEmailAuthDt(null);
        member.setEmailAuthYn(false);
        member.setEmailAuthKey("");
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        member.setUserStatus(MemberCode.MEMBER_STATUS_WITHDRAW);

        member.setZipcode("");
        member.setAddr("");
        member.setAddrDetail("");
        memberRepository.save(member);
        return new ServiceResult();
    }
}
