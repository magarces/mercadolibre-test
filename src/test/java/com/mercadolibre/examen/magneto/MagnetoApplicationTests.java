package com.mercadolibre.examen.magneto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.examen.magneto.domain.Stats;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MagnetoApplicationTests {

  @Autowired
  private WebApplicationContext appContext;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();
  }

  @Test
  public void invalidRequestInconsistentLength() throws Exception {
    String request = "{\n" +
        "	\"dna\": [\"CTXXXXT\",\n" +
        "	        \"CTCACTT\",\n" +
        "	        \"ACGCTAT\",\n" +
        "	        \"ACCTACC\",\n" +
        "	        \"CAATTCC\",\n" +
        "	        \"CACCAAC\",\n" +
        "	        \"CAACAAT\"]\n" +
        "}\n" +
        "";
    ResultActions result = mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isForbidden());
    String response = result.andReturn().getResponse().getContentAsString();
    response.contains("La información de la secuencia del dna es incorrecta.");
  }

  @Test
  public void invalidRequestInvalidNitrogenousBase() throws Exception {
    String request = "{\n" +
        "	\"dna\": [\"ACCTATC\",\n" +
        "	        \"CTCACTT\",\n" +
        "	        \"ACGCTAT\",\n" +
        "	        \"ACCTACC\",\n" +
        "	        \"CAATTCC\",\n" +
        "	        \"CC\",\n" +
        "	        \"CAACAAT\"]\n" +
        "}\n" +
        "";
    ResultActions result = mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isForbidden());
    String response = result.andReturn().getResponse().getContentAsString();
    response.contains("El tamaño de la secuencia no coincide con la de las cadenas.");
  }

  @Test
  public void test01StatsTestBegin() throws Exception {
    ResultActions result = mockMvc.perform(
        get("/stats")
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.count_mutant_dna").value(0))
        .andExpect(jsonPath("$.count_human_dna").value(0))
        .andExpect(jsonPath("$.ratio").value(0));

    byte[] content = result.andReturn().getResponse().getContentAsByteArray();

    Stats stats = new ObjectMapper().readValue(content, Stats.class);
    Assert.assertEquals(0L, stats.getHumanCount());
    Assert.assertEquals(0L, stats.getMutantCount());
    Assert.assertEquals(Double.valueOf(0), stats.getRatio());
  }

  @Test
  public void test02TestHuman() throws Exception {
    String request = "{\"dna\":[\"CCTCGA\",\"CCGTGC\",\"TAAATT\",\"AGGTGG\",\"CACCGA\",\"ACACTT\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isForbidden());
  }

  @Test
  public void test03StatsOneHumanOneDNA() throws Exception {
    test02TestHuman();
    test02TestHuman();
    test02TestHuman();
    mockMvc.perform(
        get("/stats")
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.count_mutant_dna").value(0))
        .andExpect(jsonPath("$.count_human_dna").value(1))
        .andExpect(jsonPath("$.ratio").value(0));
  }

  @Test
  public void test04StatsOneHuman() throws Exception {
    mockMvc.perform(
        get("/stats")
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.count_mutant_dna").value(0))
        .andExpect(jsonPath("$.count_human_dna").value(1))
        .andExpect(jsonPath("$.ratio").value(0));
  }

  @Test
  public void test05MutantHorizontalAndDiagnonalInverse() throws Exception {
    String request = "{\"dna\": [\"AACCCC\",\"CTCAGC\",\"ACCAAA\",\"ACAAAC\",\"CAAATC\",\"CAACAA\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isOk());
  }

  @Test
  public void test06MutantHorizontalAndDiagnonalInverse() throws Exception {
    String request = "{\"dna\": [\"AAAACC\",\"CTCACT\",\"ACCCTA\",\"ACCTAC\",\"CAACTC\",\"CAACAA\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isOk());
  }

  @Test
  public void test07HumanOneSequenceDiagnonalInverse() throws Exception {
    String request = "{\"dna\": [\"AATCCC\",\"CTCACT\",\"ACCCTA\",\"ACCTAC\",\"CAATTC\",\"CAACAA\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isForbidden());
  }

  @Test
  public void test08MutantDiagnonalAndHorizontal() throws Exception {
    String request = "{\"dna\": [\"GATACC\",\"CGCAAT\",\"ACGCTA\",\"ACCGAC\",\"CAATTC\",\"AAAAAA\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isOk());
  }

  @Test
  public void test09MutantTwoSequencesHorizontal() throws Exception {
    String request = "{\"dna\":[\"ACCCCC\",\"CTCACT\",\"ACCCTA\",\"ACCTAC\",\"CAACTC\",\"CAGGGG\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isOk());
  }

  @Test
  public void test10MutantTwoSequencesVertical() throws Exception {
    String request = "{	\"dna\": [\"CCACCT\",\"TCACTT\",\"CAATAT\",\"AAGACT\",\"AACTCC\",\"AACAAT\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isOk());
  }

  @Test
  public void test11MutantTwoSequencesVertical() throws Exception {
    String request = "{\"dna\": [\"TCACCT\",\"TCACTT\",\"TCATAG\",\"TAGACG\",\"CACTGG\",\"ATCAGG\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isOk());
  }

  @Test
  public void test12StatsMutantAndHuman() throws Exception {
    mockMvc.perform(
        get("/stats")
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.count_mutant_dna").value(6))
        .andExpect(jsonPath("$.count_human_dna").value(2))
        .andExpect(jsonPath("$.ratio").value(3));
  }

  @Test
  public void test13HumanNumberSequenceIncorrect() throws Exception {
    String request = "{\"dna\":[\"GTC\",\"GTC\",\"GTG\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isForbidden())
    ;
  }

  @Test
  public void test14MutantHorizontalAndDiagnonalInverse() throws Exception {
    String request = "{\"dna\": [\"AACCGC\",\"CTCACT\",\"ACCCCA\",\"ACGCGC\",\"CACATC\",\"CCACAA\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isOk());
  }

  @Test
  public void test15MutantDiagnonalAndDiagonalInverse() throws Exception {
    String request = "{\"dna\": [\"AACCGC\",\"CTCACT\",\"ACGCTA\",\"ACCGGC\",\"CCAAGC\",\"CCACAG\"]}";
    mockMvc.perform(
        post("/mutant/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request.getBytes()))
        .andExpect(status().isOk());
  }

}
