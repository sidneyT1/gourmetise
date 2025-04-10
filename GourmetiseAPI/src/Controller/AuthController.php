<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;

class AuthController extends AbstractController
{
    #[Route('/api/login_check', name: 'api_login_check', methods: ['POST'])]
    public function login_check()
    {
        
    }

    #[Route('/api/profile', name: 'profile', methods: ['GET'])]
    public function getProfile(): JsonResponse
    {
    
    $user = $this->getUser();
    return $this->json ($user, status: Response::HTTP_OK );
    }
}