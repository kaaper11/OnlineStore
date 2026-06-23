package service.productfacade;

import dto.product.response.ComputerResponseDto;
import dto.product.response.ElectronicsResponseDto;
import dto.product.response.ProductResponseDto;
import dto.product.response.SmartphoneResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.computer.ComputerServiceImpl;
import service.electronics.ElectronicsServiceImpl;
import service.smartphone.SmartphoneServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductFacadeServiceImplTest {

    @Mock
    private ComputerServiceImpl computerService;

    @Mock
    private ElectronicsServiceImpl electronicsService;

    @Mock
    private SmartphoneServiceImpl smartphoneService;

    @Mock
    private ComputerResponseDto compResponseDto;

    @Mock
    private ElectronicsResponseDto electronicsResponseDto;

    @Mock
    private SmartphoneResponseDto smartphoneResponseDto;

    private ProductFacadeServiceImpl productFacadeService;

    @BeforeEach
    void setUp() {
        productFacadeService = new ProductFacadeServiceImpl(
                List.of(
                        computerService,
                        electronicsService,
                        smartphoneService
                )
        );
    }

    @Test
    void shouldReturnAllProductsSortedById() {
        ComputerResponseDto product1 = mock(ComputerResponseDto.class);
        ElectronicsResponseDto product2 = mock(ElectronicsResponseDto.class);
        SmartphoneResponseDto product3 = mock(SmartphoneResponseDto.class);

        when(product1.getId()).thenReturn(3L);
        when(product2.getId()).thenReturn(1L);
        when(product3.getId()).thenReturn(2L);

        when(computerService.getAll()).thenReturn(List.of(product1));
        when(electronicsService.getAll()).thenReturn(List.of(product2));
        when(smartphoneService.getAll()).thenReturn(List.of(product3));

        List<ProductResponseDto> result = productFacadeService.getBaseProducts();

        assertEquals(3, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(3L, result.get(2).getId());
    }
}